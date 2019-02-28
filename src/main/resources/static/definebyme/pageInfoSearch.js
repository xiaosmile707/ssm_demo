var pageNo = $("#pageNo").text();
var totalPages = $("#totalPages").text();
var totalCount=$("#totalCount").text();
var count = Math.ceil(totalPages / 5);
var index = Math.ceil(pageNo / 5);
var pathName = document.location.pathname;
var urlIndex = pathName.substr(1).indexOf("/");
var result = pathName.substr(0,urlIndex+1)+"/index?pageNo=";
var state = {title:'',url:window.location.href.split("?")[0]};
history.pushState(state,'','/user/index');
$(function () {
    generate();
    active();
    disabled();
    $("#left").click(function () {
        if (index>1) {
            index--;
            generate();
            active();
            disabled();
        }
    });
    $("#right").click(function () {
        if (index<count&&count>1){
            index++;
            generate();
            active();
            disabled();
        }
    });
});
function disabled() {
    if (index === 1) {
        $("#left").parent().removeAttr("class").attr("class","disabled");
    }else {
        $("#left").parent().removeAttr("class");
    }
    if (index*5>totalPages){
        $("#right").parent().removeAttr("class").attr("class","disabled");
    }else {
        $("#right").parent().removeAttr("class");
    }
}
function active() {
    var search="page"+pageNo;
    $("#"+search).attr("class","active");
}
function generate() {
    $(".page").remove();
    if (index * 5 > totalPages) {
        for (var i = totalPages; i >= (index - 1) * 5 + 1; i--) {
            $("#firstPage").after("<li class='page' id='page"+i+"'><a href='"+result+i+"'>"+i+"</a></li>");
        }
    } else {
        for (var j = index * 5; j >= (index - 1) * 5 + 1; j--) {
            $("#firstPage").after("<li class='page'><a href='"+result+i+"'>"+i+"</a></li>");
        }
    }
}
