$(document).ready(function() {

    var canNum = 100;
    var members=[];

    getCouponsNum();
    getMemberList('');
    //以下为更改优惠金额的方法
    function getCouponsNum() {

        //该行需注释掉
        $("#send-num").text(canNum);

        //以下为与后端交互真方法
        // getRequest(
        //     '/coupon/getNum',
        //     function (res) {
        //         canNum = res.content;
        //         $("#send-num").text(canNum);
        //     },
        //     function (error) {
        //         alert(JSON.stringify(error));
        //     }
        // );
    }

    $('#send-modify-btn').click(function () {
        $("#send-modify-btn").hide();
        $("#send-set-input").val(canNum);
        $("#send-set-input").show();
        $("#send-confirm-btn").show();
    });
    //z

    $('#send-confirm-btn').click(function () {
        var dayNum = $("#send-set-input").val();

        // 以下需注释掉
        canNum = dayNum;
        getCouponsNum();
        $("#send-modify-btn").show();
        $("#send-set-input").hide();
        $("#send-confirm-btn").hide();

        //以下为与后端交互真方法
        // postRequest(
        //     '/coupon/change',
        //     {day:dayNum},
        //     function (res) {
        //         if(res.success){
        //             canNum = dayNum;
        //             getCouponsNum();
        //             $("#send-modify-btn").show();
        //             $("#send-set-input").hide();
        //             $("#send-confirm-btn").hide();
        //         } else{
        //             alert(res.message);
        //         }
        //     },
        //     function (error) {
        //         alert(JSON.stringify(error));
        //     }
        // );
    })


    //以下为展示用户的方法
    function getMemberList() {
        //以下需注释掉
        var list=[{
            username:"ezio",
            password:123456,
            id:1
        },{
            username:"router",
            password:123456,
            id:2
        }]
        renderMemberList(list);

        //以下为与后端交互真方法
        // getRequest(
        //     '/coupon/allMember',
        //     function (res) {
        //         renderMemberList(res.content);
        //     },
        //     function (error) {
        //         alert(error);
        //     });
    }

    function renderMemberList(list) {
        $('.member-card').empty();
        var memberDomStr = '';
        list.forEach(function (member) {
            memberDomStr +=
                "<tr>" +
                "   <td>" +
                "       <div>"+member.id+"</div>" +
                "   </td>" +
                "   <td>" +
                "       <div>"+member.username+"</div>" +
                "   </td>" +
                "   <td>" +
                "<div class='checkbox' id='isChoose'>"+
            "<label><input class='choose' type='checkbox' value='' data-member='"+JSON.stringify(member)+"'><span id='id-num'>选择</span></label>"+
            "   </td>" +
            "</tr>"
        });
        $("#myTable").append(memberDomStr);
    }
//以下为选中某些用户的方法

});