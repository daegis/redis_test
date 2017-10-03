<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Redis测试</title>
    <script src="${pageContext.request.contextPath}/js/jquery-1.12.4.js"></script>
    <script>
        $(function () {
            $("#sreset").click(function () {
                $(".save").val("");
                $("#sresult").html("&nbsp;");
            });
            $("#ssubmit").click(function () {
                $.ajax({
                    url: '${pageContext.request.contextPath}/save',
                    type: 'post',
                    data: {key: $("#skey").val(), value: $("#svalue").val()},
                    dataType: 'json',
                    success: function (data) {
                        if (data.success) {
                            $("#sresult").html("<font color='red'>ok</font>");
                        } else {
                            $("#sresult").html("<font color='red'>" + data.message + "</font>");
                        }
                    }
                })
            });


            $("#rreset").click(function () {
                $(".read").val("");
                $("#rresult").html("&nbsp;");
            });
            $("#rsubmit").click(function () {
                $.ajax({
                    url: '${pageContext.request.contextPath}/read',
                    type: 'post',
                    data: {key: $("#rkey").val()},
                    dataType: 'json',
                    success: function (data) {
                        if (data.success) {
                            $("#rresult").html(data.message);
                        } else {
                            $("#rresult").html("<font color='red'>" + data.message + "</font>");
                        }
                    }
                })
            })
        })
    </script>
</head>
<body>
<h1>Redis测试</h1>
<hr>
<h2>存储测试</h2>
<table>
    <tr>
        <td>key</td>
        <td><input class="save" name="skey" id="skey"></td>
    </tr>
    <tr>
        <td>value</td>
        <td><input class="save" name="svalue" id="svalue">&nbsp;<span id="sresult">&nbsp;</span></td>
    </tr>
    <tr>
        <td></td>
        <td>
            <button id="sreset">重置</button>
            <button id="ssubmit">提交</button>
        </td>
    </tr>
</table>
<hr>
<h2>读取测试</h2>
<table>
    <tr>
        <td>key</td>
        <td><input class="read" name="rkey" id="rkey">&nbsp;<span id="rresult">&nbsp;</span></td>
    </tr>
    <tr>
        <td></td>
        <td>
            <button id="rreset">重置</button>
            <button id="rsubmit">提交</button>
        </td>
    </tr>
</table>
</body>
</html>
