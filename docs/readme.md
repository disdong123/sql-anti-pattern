#

## 2. 배열처럼 여러개의 값을 가지는 컬럼의 데이터는 어떻게 저장해야 할까?
만약 product 와 account 틑 원래 1:N 관계라면 product 에 하나의 account_id 가 오게 됩니다.

### 한 product 에 여러 account 가 붙을 수도 있게 되면?
즉, 1:N 이였던 관계가 N:N 관계로 변경되는 상태로,

product 의 account_id 에 "," 를 기준으로 나누어 데이터를 저장하는 것 보단, 새로 테이블을 만드는 것이 좋습니다.

다만, 쉼표로 구분된 형식의 데이터가 필요하고, 목록안의 개별 데이터에는 관심이 전혀 없을땐, 반정규화하여 쉼표로 구분하여 성능을 높일 수 있습니다.


## 3. 계층형 댓글
### 인접 리스트
가장 간단한 방법은 comment 테이블 내에 parent_id 로 부모 댓글을 참조하여 구현할 수 있으며, 이 설계는 인접 리스트라고도 불립니다.

```sql
CREATE TABLE comment(
                         id         INT             PRIMARY KEY ,
                         parent_id  INT,
                         bug_id     INT             NOT NULL ,
                         author_id     INT          NOT NULL ,
                         comment    TEXT            NOT NULL ,
                         FOREIGN KEY (parent_id)    REFERENCES comment(id),
                         FOREIGN KEY (bug_id)       REFERENCES bug(id),
                         FOREIGN KEY (author_id)    REFERENCES account(id)
);
```

이 설계방식은 주어진 노드의 부모나 자식을 바로 얻을 수 있고, 새로운 노드를 추가하는 것은 쉽지만, 계층이 깊어질수록 전체 조회 쿼리를 작성하는 것이 복잡해집니다. 
즉, 자식 혹은 손자까지의 고정된 깊이까지 불러오는 것은 별로 어렵지 않지만 전체 깊이를 조회하는것은 어려울 수 있습니다.

```sql
SELECT              *
FROM                comment c1
LEFT OUTER JOIN     comment c2
ON                  c2.parent_id = c1.id
LEFT OUTER JOIN     comment c3
ON                  c3.parent_id = c2.id
...
```
특정 댓글에 대한 전체 자식 댓글을 가져오는 sql 을 작성하기 까다로우므로, bug_id 에 해당하는 전체 댓글을 가져와서 어플리케이션 단에서 트리 구조로 만들 수도 있지만 이 역시 비효율적입니다. 

**다만, WITH RECURSIVE 와 같은 재귀적 쿼리 문법을 지원하는 경우에는 조회 쿼리를 작성할 수 있습니다.**

### Closure table 을 사용하자.
클로저 테이블은 부모-자식 관계 뿐만아니라 트리의 모든 경로를 저장하는 방식으로, 책에서 계층구조를 저장하는 단순하고 우아한 방법으로 묘사됩니다.

```sql
CREATE TABLE tree_path(
                          ancestor   INT NOT NULL,
                          descendant INT NOT NULL,
                          PRIMARY KEY (ancestor, descendant),
                          FOREIGN KEY (ancestor) REFERENCES comment(id),
                          FOREIGN KEY (descendant) REFERENCES comment(id)
);
```
comment 에 별도 parent_id 와 같은 컬럼을 추가하지 않고, 별도 테이블에서 모든 관계를 정의합니다. 

조회/수정 등에 소요되는 계산은 줄어들지만 저장되는 행의 수가 많아질 수 있습니다.

## 4. N:N 으로 생성된 테이블에서 ID 컬럼이 꼭 필요한가?
만약 N:N 관계에서 생성된 추가 테이블이 있을 때, 이 테이블의 PK 는 무엇으로 해야할까?

1. AUTO_INCREMENT 되는 ID 컬럼을 생성하고 PK 로 한다(가상키). 다만, FK 가 중복되는 로우가 생길 수도 있으므로 묶어서 UNIQUE 로 지정해주어야 한다.
2. 해당 테이블의 FK 를 묶어서 PK 로 한다.

저자의 생각은 굳이 1번으로 해야할까? 또한, 1번으로 하더라도 컬럼명이 ID 인 것도 MEMBER_ID 로 하는 것이 좋다고 생각한다.

만약 사용하는 ORM 의 제약사항에 복합키는 지원하지 않는다는 부분이 없다면 그냥 FK 두개를 묶어서 PK 로 활용하는 것이 더 좋다.

다만, FK 가 4, 5개를 넘어선다면 가상키를 만드는 것이 좋을것이라 한다.


## 5. FK 제약조건이 필요한가?
FK 제약조건이 있으면 쿼리가 복잡해질 수 있고, 인덱스가 생성되어 비효율적일수 있다.

그래도 데이터 정합성, cascade 등이 훨씬 더 좋으므로 FK 를 사용하는것이 좋다.


## 6. 엔티티 - 속성 - 값: 두 테이블에 공통된 속성이 있을땐 어떻게 해야할까?
관계형 DB 는 원래 런타임 상에서 컬럼의 추가, 삭제가 불가능하여 유연함이 떨어진다.

이를 해결하기 위해, 하나의 테이블의 속성과 값을 정의하는 테이블을 별도로 두어 런타임시에도 여러 컬럼을 추가할 수 있는 방법이 있다.

```sql
CREATE issue(
    id INT PRIMARY KEY
)
    
CREATE issue_attribute(
    issue_id        INT,
    attribute_name  varchar(100),
    attribute_value varchar(100),
    PRIMARY KEY (issue_id, attribute_name),
    FOREIGN KEY (issue_id) REFERENCES issue(id)   
)

INSERT INT issue_attribute values (1234, 'created_at', '2022-10-10 01:01:01')
INSERT INT issue_attribute values (1234, 'updated_at', '2022-10-10 01:01:01')
```

이러한 설계를 엔티티-속성-값 또는 EAV 라고 하는데, 이렇게 컬럼이 유동적이면 유연해지지만 조회쿼리가 매우 복잡해져서 명백한 안티패턴이다.

따라서 EAV 보단 차라리 각 테이블에 모든 속성을 넣는게 더 쉬울 수 있는데, 이러면 어떤 컬럼이 공통된 컬럼인지 확인하기가 힘들다. 

### Class table inheritance 을 사용하자.
말은 거창하지만 단순하다.

만약, bug, feature_request 라는 애들이 있고, bug, feature_request 는 공통된 컬럼도 있고, 자기 자신만의 컬럼이 있다고 하자.

그냥, 공통 컬럼을 가지고 있는 테이블 하나와 자기만의 컬럼을 가지는 테이블을 따로 만들어 외래키로 묶는 방법이다.

불필요하게 null 을 넣을 필요도 없고, 조회도 쉽게 할 수 있다.


## 7. 다형성 연관 - 댓글을 여러곳에서 달 수 있을때 FK 는 무엇으로 설정해야 될까?
댓글이 bug 에도 달릴 수 있고, feature_request 에도 달릴수있으면 어떻게 구성해야 할까?

한가지 방법은 아래처럼 issue_type 을 두어 구분할 수 있다.

```sql
create table comment(
    id      int primary key ,
    issue_type  varchar,     -- bug 또는 featureRequest
    issue_id    int,
    author      int,
    foreign key (author) references account(id)
);
```

만약 위 처럼 한다면 조회할 떄 항상 issue_type 을 명시해주어야한다.

```sql
select *
from bug
         inner join comment on issue_id = comment.id
where id = 1000
--   and issue_type = "bug";
```

위처럼 명시하지 않으면 issue_id 가 1000 인 feature_request 도 함께 조회된다. 


이 방법은 issue_id 에 FK 를 걸 수 없어 데이터 정합성을 보장할 수 없다.

만약 FK 로 해야할것같지만 FK 를 선언하면 안되거나, issue_type 처럼 각 행의 부모테이블이 뭔지 알려주는 용도의 컬럼이 있으면 이러한 안티패턴일 수 있다.


EAV 와 마찬가지로 유연성을 보장해주지만 안티패턴이다.

물론, 하이버네이트같이 코드상에서 다형성 연관을 지원해줄 수 있는 라이브러리를 사용한다면 좋은 방법일 수도 있다. (확인해보기..)


### 교차테이블을 만들어서 역으로 참조하기

자식인 comment 의 FK 는 bug, feature_request 등 여러 부모테이블을 참조할 수 없으므로, comment 테이블을 참조하는 여러개의 FK 를 사용하도록 한다.

이를 위해 comment 와 bug 를 위한 별도 테이블을 추가해준다. 대신, 하나의 댓글이 여러개의 bug 와 연관될 수 없도록 comment_id 에 unique 를 걸어준다.

```sql
create table bug_comment(
    issue_id        int not null ,
    comment_id      int not null ,
    unique key (comment_id),
    primary key (issue_id, comment_id),
    foreign key (issue_id) references bug(id),
    foreign key (comment_id) references comment(id),
);
```

조회할 때 type 을 지정하지 않고 교차 테이블을 이용하여 조회할 수 있다.

```sql
select *
from bug_comment
        inner join comment c2 on c2.id = bug_comment.comment_id
where bug_comment.issue_id = 1000;
```

**하나의 단점은 bug_comment 와 feature_request_comment 양 쪽에 동일한 comment_id 가 들어가서 정합성을 깰 수 있다. 이는 어플리케이션에서 막아주어야 한다.**

 
만약 6의 class-table-inheritance 처럼 bug 와 feature_request 가 공통으로 참조하는 issue 테이블이 있다면 issue 테이블의 id 를 comment 가 FK 로 갖고있으면 모든게 쉽게 해결된다.

다만, 꼭 이렇게 공통 테이블을 갖고 있을거라고 보장할 수 없다.


## 8. 다중속성컬럼 - 속성값이 여러개일 경우에 어떻게 할까?

연락처같은걸 저장하려면 어떻게 해야될까? 기본적으로 집, 회사, 휴대폰, 팩스 등 여러 번호들이 존재할 수 있는데 전부 컬럼으로 만들어야 할까?


일단 bug 를 예로 보자.

만약 bug 에 여러 태그를 달 수 있다고 하면 bug 와 bug_tag 를 따로 분리하는 것이 좋다.
```sql
-- 안티패턴
create table bug(
    bug_id  int not null,
    tag1    varchar,
    tag2    varchar,
    tag3    varchar
);


-- 이렇게 따로 만들어주자.
create table bug_tag(
    bug_id int not null,
    tag     varchar(10)
);
```

연락처도 이와 비슷하게 할 수 있을거 같다. (책에서는 방법을 제시해주지 않음...)

```sql
create number(
    user_id     int not null,
    type        varchar,        -- 어떤 연락처인지 타입 적기
    number      varchar
);
```

7번에선 type 같은걸 안쓰는게 좋다고 했는데, 이건 FK 를 결정할 수 없을때 type 으로 구분하는 것을 의미한다.

따라서 여기서는 해도 될것같다.






