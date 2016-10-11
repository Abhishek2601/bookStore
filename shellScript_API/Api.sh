#/usr/bin

##create db
mysql -u root book < bookdump.sql
## add user
curl -XPOST -H 'content-type:application/json' http://localhost:8080/user/register -d '{"name":"abhishek","age":26, "type":"BASIC"}'

curl -XPOST -H 'content-type:application/json' http://localhost:8080/user/register -d '{"name": "Ajay", "age":26, "type":"BASIC"}'


curl -XPOST -H 'content-type:application/json' http://localhost:8080/user/register -d '{"name":"Ajay", "age":26, "type":"BASIC"}'

curl -XPOST -H 'content-type:application/json' http://localhost:8080/user/register -d '{"name":"Aman", "age":15, "type":"BASIC"}'


## add books

curl -XPOST -H 'content-type:application/json' http://localhost:8080/book/add -d  '
{"book_dsc":{"name":"HarryPotter" ,"type":"story"},"num_of_books":2}'

curl -XPOST -H 'content-type:application/json' http://localhost:8080/book/add -d '{"book_dsc" :{"name":"HarryPotter1" , "type":"story"},"num_of_books":2}'

curl -XPOST -H 'content-type:application/json' http://localhost:8080/book/add -d '{"book_dsc" :{"name":"HarryPotter" , "type":"story"},"num_of_books":1}'

curl -XPOST -H 'content-type:application/json' http://localhost:8080/book/add -d '{"book_dsc" :{ "name":"ChachaChoudhary" , "type":"comics"},"num_of_books":1}'

## book Issue

curl -XPOST -H 'content-type:application/json' http://localhost:8080/user/issue -d '{"book_name":"Let us C", "user_name":"abhishek"}'

curl -XPOST -H 'content-type:application/json' http://localhost:8080/user/issue -d '{"book_name":"HarryPotter", "user_name":"Ajay"}'

curl -XPOST -H 'content-type:application/json' http://localhost:8080/user/issue -d '{"book_name" : "HarryPotter" , "user_name" : "Sambha"}'

curl -XPOST -H 'content-type:application/json' http://localhost:8080/user/issue -d '{"book_name":"ChachaChoudhary", "user_name":"abhishek"}'

curl -XPOST -H 'content-type:application/json' http://localhost:8080/user/issue -d '{"book_name":"HarryPotter", "user_name":"Ajay"}'

curl -XPOST -H 'content-type:application/json' http://localhost:8080/user/issue -d '{"book_name" : "ChachaChoudhary" , "user_name" : "Ajay"}'

##book return

curl -XPOST -H 'content-type:application/json' http://localhost:8080/user/returnbook -d '{"book_name":"Let us C", "user_name":"abhishek"}'

curl -XPOST -H 'content-type:application/json' http://localhost:8080/user/returnbook -d '{"book_name":"HarryPotter", "user_name":"Ajay"}'

curl -XPOST -H 'content-type:application/json' http://localhost:8080/user/returnbook -d '{"book_name" : "HarryPotter" , "user_name" : "Sambha"}'

curl -XPOST -H 'content-type:application/json' http://localhost:8080/user/returnbook -d '{"book_name":"ChachaChoudhary", "user_name":"abhishek"}'

curl -XPOST -H 'content-type:application/json' http://localhost:8080/user/returnbook -d '{"book_name":"HarryPotter", "user_name":"abhishek"}'

curl -XPOST -H 'content-type:application/json' http://localhost:8080/user/returnbook -d '{"book_name" : "ChachaChoudhary" , "user_name" : "Ajay"}'





