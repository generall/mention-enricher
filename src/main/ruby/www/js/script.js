$(function () {
  $(".mention").hover(function(){
    var id = $(this).attr("mention_id")
    $("[mention_id=" + id + "]").css("background-color","#FFB66A")
  }, function(){
    var id = $(this).attr("mention_id")
    $("[mention_id=" + id + "]").css("background-color","#fff")
  })
});
