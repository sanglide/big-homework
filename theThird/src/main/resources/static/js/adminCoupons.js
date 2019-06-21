$(document).ready(function() {

    var canNum = 100;
    var buyNum=100;
    var members=[];

    getCouponsList();
    getCouponsNum();
    getBuyNum();

    //以下为获取会员名单的方法
    function getCouponsList(){
        // 以下需注释掉
//        var list1=[{
//            username:"ezio",
//            password:123456,
//            id:1
//        },{
//            username:"router",
//            password:123456,
//            id:2
//        }]
//        renderMemberList(list1);

//        以下为与后端交互真方法
         getRequest(
             '/ticket/allMember?consume='+buyNum,
             // {buyNum:buyNum},
             function (res) {
                 console.log(res);
                 var list=res.content;
                 console.log(list);
                 renderMemberList(list);

             },
             function (error) {
                 alert(JSON.stringify(error));
             }
         );

    }

    //以下为更改优惠金额的方法
    function getCouponsNum() {

        //该行需注释掉
        // $("#send-num").text(canNum);

        //以下为与后端交互真方法
        getRequest(
            '/coupon/getNum',
            function (res) {
                canNum = res.content;
                console.log(canNum);
                $("#send-num").text(canNum);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
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
        // canNum = dayNum;
        // getCouponsNum();
        // $("#send-modify-btn").show();
        // $("#send-set-input").hide();
        // $("#send-confirm-btn").hide();

        //以下为与后端交互真方法
        getRequest(
            '/coupon/change?discount='+dayNum,
            // {discount:dayNum},
            function (res) {
                if(res.success){
                    canNum = dayNum;
                    getCouponsNum();
                    $("#send-modify-btn").show();
                    $("#send-set-input").hide();
                    $("#send-confirm-btn").hide();
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );

    })
    //以下为修改最低消费的方法
    function getBuyNum() {
        $("#buy-num").text(buyNum);
    }

    $('#buy-modify-btn').click(function () {
        $("#buy-modify-btn").hide();
        $("#buy-set-input").val(buyNum);
        $("#buy-set-input").show();
        $("#buy-confirm-btn").show();
    });
    //z

    $('#buy-confirm-btn').click(function () {
        var bNum = $("#buy-set-input").val();

        buyNum = bNum;
        getBuyNum();
        $("#buy-modify-btn").show();
        $("#buy-set-input").hide();
        $("#buy-confirm-btn").hide();
        getCouponsList();

    })

    //以下为展示用户的方法

    function renderMemberList(list) {
        $('#myTable').empty();
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
                "<div>"+
            "<input name='optionName' type='checkbox' data-member='"+JSON.stringify(member)+"'>选择</div>"+
            "   </td>" +
            "</tr>"
        });
        $("#myTable").append(memberDomStr);
    }
//以下为选中某些用户的方法
    $(document).on('click',"input[type='checkbox']",function (e) {
        var memberInfo=JSON.parse(e.currentTarget.dataset.member);
        console.log(memberInfo);
        //refund.id是电影票的id
        if($(this).prop('checked')){
            members.push(memberInfo.id);
        }else{
            if(members.indexOf(memberInfo.id)>-1){
                members.splice(members.indexOf(memberInfo.id),1);
            }
        }
        console.log(members);

    })


    sendConfirmClick=function(){
        getRequest(
            '/coupon/send?userId='+members,
            // {members:members},
            function (res) {
                if(res.success){
                    alert("赠送成功");
                    getCouponsList();
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }
});