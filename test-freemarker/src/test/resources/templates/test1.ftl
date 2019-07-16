<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    helle ${key}
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>金额</td>
        <#--<td>出生日期</td>-->
    </tr>
    <#list stus as stu>
        <tr>
            <td>${stu_index}</td>
            <td>${stu.name}</td>
            <td>${stu.age}</td>
            <td>${stu.mondy}</td>
            <#--<td>${stu.birthday}</td>-->
        </tr>

    </#list>
</table>
</body>
</html>