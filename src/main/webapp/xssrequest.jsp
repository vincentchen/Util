<%--
 @author  Vincent.Chan
 @since   2012.06.21 21:21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<!doctype html>
<head>
    <%--<script type="text/javascript"src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"/>--%>
    <script type="text/javascript" src="http://fool2fish.aliapp.com/rdss/http/xhr.js"/>
    <script type="text/javascript">

    </script>
    <title></title>
</head>
<body>
<div id="page">
    <div id="header">
        <h1>Cross Domain XHR</h1>
        <ul class="links">
            <li><a href="/">demo 首页</a></li>
        </ul>
    </div>
    <div id="content">
        <br/>

        <div style="color:#f30;">注意：demo 不支持ie8及更低版本。</div>
        <br/>
        <button id="not-allow">not allow cross domain xhr</button>
        <script>
            document.getElementById('not-allow').onclick = function (ev) {
                xhr({
                    method:'get',
                    url:'http://120.33.35.32:8081/ibaby/appstore/cssrequest',
                    params:'name=fool2fish',
                    cb:function (resp) {
                        alert(resp);
                    }
                });
            }
        </script>
        <br/>
        <br/>
        <button id="simple-get">send a simple xd get request</button>
        <script>
            document.getElementById('simple-get').onclick = function (ev) {
                xhr({
                    method:'get',
                    url:'http://120.33.35.32:8081/ibaby/appstore/cssrequest',
                    params:'name=fool2fish',
                    cb:function (resp) {
                        alert(resp);
                    }
                });
            }
        </script>
        <br/>
        <br/>
        <button id="simple-post">send a simple xd post request</button>
        <script>
            document.getElementById('simple-post').onclick = function (ev) {
                xhr({
                    method:'post',
                    url:'http://120.33.35.32:8081/ibaby/appstore/cssrequest',
                    params:'name=fool2fish',
                    cb:function (resp) {
                        alert(resp);
                    }
                });
            }
        </script>
        <br/>
        <br/>
        <button id="get">send a xd get request</button>
        <script>
            document.getElementById('get').onclick = function (ev) {
                xhr({
                    method:'get',
                    url:'http://120.33.35.32:8081/ibaby/appstore/cssrequest',
                    params:'name=fool2fish',
                    cb:function (resp) {
                        alert(resp);
                    },
                    headers:{theme:'crossdomainxhr'}// demo中服务器仅允许了这一个自定义头域
                });
            }
        </script>
        <br/>
        <br/>
        <button id="post">send a xd post request</button>
        <script>
            document.getElementById('post').onclick = function (ev) {
                xhr({
                    method:'post',
                    url:'http://120.33.35.32:8081/ibaby/appstore/cssrequest',
                    params:'name=fool2fish',
                    cb:function (resp) {
                        alert(resp);
                    },
                    headers:{theme:'crossdomainxhr'}
                });
            }
        </script>
        <br/>
        <br/>
        <button id="with-credentials">send a xd post request with credetials</button>
        <script>
            document.getElementById('with-credentials').onclick = function (ev) {
                xhr({
                    method:'post',
                    url:'http://120.33.35.32:8081/ibaby/appstore/cssrequest',
                    params:'name=fool2fish',
                    cb:function (resp) {
                        alert(resp);
                    },
                    headers:{theme:'crossdomainxhr'},
                    withCredentials:true
                });
            }
        </script>

    </div>
</div>
</body>
</html>