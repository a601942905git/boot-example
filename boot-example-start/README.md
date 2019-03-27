SQL注入的问题
# 控制器方法
```java
@GetMapping("/name")
public StudentEntity getStudentByName() {
    String desc = "'' and 1 = 1;DELETE FROM student;";
    return studentService.getStudentByName(desc);
}
```
# 业务方法
```java
public StudentEntity getStudentByName(String desc) {
    return studentMapper.getStudentByName(desc);
}
```
# mapper方法
```java
StudentEntity getStudentByName(String desc);
```
# 执行SQL语句
```
<select id="getStudentByName" parameterType="string" resultType="com.boot.example.core.entity.StudentEntity">
    SELECT
        <include refid="BASE_SELECT_COLUMN"/>
    FROM
        student
    WHERE stu_desc = ${_parameter}
</select>
```
# 日志查看预编译SQL为
```
2019-03-27 16:14:31.391 DEBUG 35724 --- [nio-8888-exec-3] c.b.e.c.m.S.getStudentByName             : ==>  Preparing: SELECT id, name, age FROM student WHERE stu_desc = '' and 1 = 1;DELETE FROM student; 
```
如上结果可以看到使用$方法会直接将用户输入的数据拼接在SQL中，然后执行，执行的结果就是讲表中的数据全部删除

# 使用#方式
```
<select id="getStudentByName" parameterType="string" resultType="com.boot.example.core.entity.StudentEntity">
    SELECT
        <include refid="BASE_SELECT_COLUMN"/>
    FROM
        student
    WHERE stu_desc = #{_parameter}
</select>
```

# 预编译SQL
```
SELECT id, name, age FROM student WHERE stu_desc = ? 
```
也就是在执行SQL之前会进行预编译，输入的地方使用？代替，当真正执行的时候才将用户输入的参数替换?

真正执行的SQL
```
SELECT
	id,
	NAME,
	age 
FROM
	student 
WHERE
	stu_desc = ''' and 1 = 1;DELETE
FROM student;';
```
可以看到输入参数替换?的时候会加上引号，用户输入的部分会被当成字符串，这样就避免了SQL注入的问题