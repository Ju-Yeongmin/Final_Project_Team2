<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Chagok | Log in</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.7 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath }/resources/bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="${pageContext.request.contextPath }/resources/plugins/font-awesome/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="${pageContext.request.contextPath }/resources/plugins/iconicons/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="${pageContext.request.contextPath }/resources/dist/css/AdminLTE.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="${pageContext.request.contextPath }/resources/plugins/iCheck/square/blue.css">
  <!-- jQuery 3 -->
  <script src="${pageContext.request.contextPath }/resources/plugins/jQuery/jquery.min.js"></script> 
  <!-- login 유효성 검사 -->
  <script src="${pageContext.request.contextPath }/resources/plugins/duplicate/login.js"></script>
  <!-- textstyle.css -->
  <link rel="stylesheet" href="${pageContext.request.contextPath }/resources/plugins/duplicate/textstyle.css">
  
  <!-- Google Font -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
  <link rel="preconnect" href="https://fonts.googleapis.com"><link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Hi+Melody&display=swap" rel="stylesheet">
  <link rel="preconnect" href="https://fonts.googleapis.com"><link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap" rel="stylesheet">
  <link rel="stylesheet" type="text/css" href="../resources/mainpagecss/css/font.css">
  
  <!-- sweetalert -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.css">
  <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.js"></script>
  
 <style>
 .btn-primary{
 background-color: #66BB7A;
    border-color: #66BB7A;
 }
 
 .btn-primary:hover{
 background-color: #539763;
    border-color: #539763;
 }
 
 
 .text-center{
   text-align: center;
   display: flex;
   justify-content: flex-end;
 }
 
 .checkbox label, .radio label{
 	min-height: 20px;
    padding-left: 10px;
    margin-bottom: 0;
    font-weight: 400;
    cursor: pointer;
 }
 
 .login-logo2 {
    border: 0;
    width: 150px;
    margin-right: 15px;
    text-align: center;
    display: block; 
    margin: 0px auto;
}
 
 </style>
 
  
</head>




<!-- 로딩 코드 start -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<style type="text/css">
#waiting {
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    position: fixed;
    display: flex;
    background: white;
    z-index: 999;
    opacity: 0.9;
}
#waiting > img {
    display: flex;
    width: fit-content;
    height: fit-content;
    margin: auto;
}
</style>
<div id="waiting">
   <img src="./resources/imgUpload/new-loading.gif">
</div>

<script type="text/javascript">
    $(window).on('load', function() {
        setTimeout(function(){
            $("#waiting").fadeOut();
        }, 300);
    });
</script>
<!-- 로딩 코드 end -->





<body class="hold-transition login-page" style="font-family: 'GmarketSans';">
<div class="login-box">
  <div class="login-logo2" text-align: center;>
  	<a href="#" style="font-family: 'GmarketSans';"><img src="${pageContext.request.contextPath }/resources/dist/img/login-logo.png" class="login-logo2"></a>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg" style="font-family: 'GmarketSans'; font-size: 18px;"><b>로그인</b></p>
	<!-- <p class="login-box-msg">Sign in to start your session</p> -->
	
    <form action="/login" method="post"> 
      <div class="form-group has-feedback"> 
        <input type="email" class="form-control" id="id" name="id"  style="font-size: 12px;" placeholder="이메일">

        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" id="pw" name="pw"  style="font-size: 12px;" placeholder="비밀번호">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <input type="hidden" value="${param.pageInfo }" class="form-control" id="pageInfo" name="pageInfo">
      <div class="row">
        <div class="col-xs-8">
          <div class="checkbox icheck">
              <div class="save_id">
              <input type="checkbox" id="save_id" name="save_id"><label for="save_id">아이디 저장 </label>
              </div>
          </div>
        </div>
        <!-- /.col -->
        <div class="col-xs-12">
          <div class="checkbox icheck">
          </div>
        </div>
        <div class="col-xs-12 pull-right">
          <button type="button" class="btn btn-primary btn-block btn-flat" onclick="f_loginCheck();">로그인</button>
        </div>
        <!-- /.col -->
      </div>
    </form>

<!--     <div class="social-auth-links text-center"> -->
<!--       <p>- OR -</p> -->
<!--       <a href="#" class="btn btn-block btn-social btn-facebook btn-flat"><i class="fa fa-facebook"></i> Sign in using -->
<!--         Facebook</a> -->
<!--       <a href="#" class="btn btn-block btn-social btn-google btn-flat"><i class="fa fa-google-plus"></i> Sign in using -->
<!--         Google+</a> -->
<!--     </div> -->
    <!-- /.social-auth-links -->

<!--     <a href="#">비밀번호 찾기</a><br> -->
    <br>
    <a href="/register" class="text-center">회원 가입</a>

  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->


<!-- Bootstrap 3.3.7 -->
<script src="${pageContext.request.contextPath }/resources/bootstrap/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="${pageContext.request.contextPath }/resources/plugins/iCheck/icheck.min.js"></script>
</body>
</html>