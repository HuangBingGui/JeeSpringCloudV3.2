<%@ page contentType="text/html;charset=UTF-8" %>
<div class="btn-group">
    <button class="btn btn-default dropdown-toggle btn-sm" data-toggle="dropdown" type="button" aria-expanded="false">
        <i class="glyphicon glyphicon-th icon-th"></i>
        <span class="caret"></span>
    </button>
    <ul class="dropdown-menu" role="menu">
        <li data-type="放大"><a href="javascript:void(0)" onclick="$('body').css({zoom:Number($('body').css('zoom'))+0.1})">放大</a></li>
        <li data-type="缩小"><a href="javascript:void(0)" onclick="$('body').css({zoom:$('body').css('zoom')-0.1})">缩小</a></li>
    </ul>
</div>