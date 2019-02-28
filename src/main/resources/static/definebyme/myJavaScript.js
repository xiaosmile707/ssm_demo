function checkForm() {
    var userName=$("#userName").val();
    var email=$("#email").val();
    var password=$("#password").val();
    var rePassword=$("#rePassword").val();
    if (userName == null ||userName === ""||userName.indexOf(" ") !== -1){
        $("#msg").text("用户名为空或存在空格");
        return false;
    }
    if (email == null||email === ""){
        $("#msg").text("邮箱不能为空");
        return false;
    }
    if (password == null||password === ""||password.indexOf(" ") !== -1){
        $("#msg").text("密码不能为空或存在空格");
        return false;
    }
    if ((password != null||password !== "")&&(password !== rePassword)){
        $("#msg").text("两次输入密码不一致");
        return false
    }
    return true;
}
