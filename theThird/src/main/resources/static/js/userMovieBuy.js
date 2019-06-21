//这个是用户买电影票用到的界面
var selectedSeats = []//这是一个 Array,里面装的是一个个的数组 e.g.selectedSeat=[[0,0],[0,5]],数组代表选的座位的相应行、列
var scheduleId;
var order = {ticketId: [], couponId: 0};
//优惠券，需要从后端获得
var coupons = [];
var ticketList=[];
var isVIP = false;
var useVIP = true;
var TicketID=[];
var couponIndexNow=0;
//已选座位、排片ID、order（票ID、优惠券ID）、VIP

//getInfo函数调用renderSchedule以显示影厅座位情况（初始时）
//点击座位触发seatClick函数，如id=seat06,0,6，完成座位的动态显示与记录
//点击确认订单触发orderConfirmClick函数，调用renderOrder函数完成订单显示，调用getRequest完成支付弹窗
//switchPay方法用来切换支付方式（弹窗里）
//通过changeCoupon方法动态显示更改优惠券
//payConfirmClick方法为确认支付
//postRequest展示了支付成功页面
//validateForm检查账号密码是否正确
$(document).ready(function () {
    //获得排片id
    scheduleId = parseInt(window.location.href.split('?')[1].split('&')[1].split('=')[1]);

    getInfo();

    function getInfo() {
        //向服务器请求数据
        getRequest(
            '/ticket/get/occupiedSeats?scheduleId=' + scheduleId,//向前端传参数
            function (res) {
                if (res.success) {//res.content是scheduleWithSeatVO
                    renderSchedule(res.content.scheduleItem, res.content.seats);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }
});

function renderSchedule(schedule, seats) {
    //设置选座界面中右侧的信息和支付界面的表中的信息
    $('#schedule-hall-name').text(schedule.hallName);
    $('#order-schedule-hall-name').text(schedule.hallName);
    $('#schedule-fare').text(schedule.fare.toFixed(2));
    $('#order-schedule-fare').text(schedule.fare.toFixed(2));
    $('#schedule-time').text(schedule.startTime.substring(5, 7) + "月" + schedule.startTime.substring(8, 10) + "日 " + schedule.startTime.substring(11, 16) + "场");
    $('#order-schedule-time').text(schedule.startTime.substring(5, 7) + "月" + schedule.startTime.substring(8, 10) + "日 " + schedule.startTime.substring(11, 16) + "场");

    //设置选座界面的左侧座位情况
    var hallDomStr = "";
    var seat = "";
    for (var i = 0; i < seats.length; i++) {//行
        var temp = "";
        for (var j = 0; j < seats[i].length; j++) {//列
            var id = "seat" + i + j;

            if (seats[i][j] === 0) {
                // 未选
                //这个座位未被占用，你可以选择该座位
                //onclick:点击这个按钮执行该文件中的seatClick(id,i,j)方法
                temp += "<button class='cinema-hall-seat-choose' id='" + id + "' onclick='seatClick(\"" + id + "\"," + i + "," + j + ")'></button>";
            } else {
                // 已选中
                //这个座位已经被占用了，你不能选择该座位
                temp += "<button class='cinema-hall-seat-lock'></button>";
            }
        }
        seat += "<div>" + temp + "</div>";
    }
    var hallDom =
        "<div class='cinema-hall'>" +
        "<div>" +
        "<span class='cinema-hall-name'>" + schedule.hallName + "</span>" +
        "<span class='cinema-hall-size'>" + seats.length + '*' + seats[0].length + "</span>" +
        "</div>" +
        "<div class='cinema-seat'>" + seat +
        "</div>" +
        "</div>";
    hallDomStr += hallDom;

    $('#hall-card').html(hallDomStr);
}

//在选座位时，点击座位时触发以下函数
function seatClick(id, i, j) {
    //let用来声明块级作用域的变量
    let seat = $('#' + id);
    if (seat.hasClass("cinema-hall-seat-choose")) {
        //点白变绿
        seat.removeClass("cinema-hall-seat-choose");
        seat.addClass("cinema-hall-seat");

        selectedSeats[selectedSeats.length] = [i, j]
        //selectedSeats是一个类似的二维数组?中为座位行、列，居然可以自由增长
        // 在末端新增内容
    } else {
        //点绿变白
        seat.removeClass("cinema-hall-seat");
        seat.addClass("cinema-hall-seat-choose");

        selectedSeats = selectedSeats.filter(function (value) {//删除座位
            return value[0] !== i || value[1] !== j;
        })
    }

    //显示座位信息时按顺序排
    selectedSeats.sort(function (x, y) {
        var res = x[0] - y[0];
        return res === 0 ? x[1] - y[1] : res;
    });

    //设置选座界面的“座位：”信息
    let seatDetailStr = "";
    if (selectedSeats.length === 0) {
        seatDetailStr += "还未选择座位"
        //确认下单的按钮禁用
        $('#order-confirm-btn').attr("disabled", "disabled")
    } else {
        //for……of 返回的是值，for……in 返回的是索引（下标）
        for (let seatLoc of selectedSeats) {//seatLoc是一个数组，里面是座位的横纵坐标
            seatDetailStr += "<span>" + (seatLoc[0] + 1) + "排" + (seatLoc[1] + 1) + "座</span>";
        }
        //解除按钮禁用
        $('#order-confirm-btn').removeAttr("disabled");
    }
    $('#seat-detail').html(seatDetailStr);
}

//当点击“选座”界面的确认下单按钮时，触发此段代码
function orderConfirmClick() {
    var form;
    //以下两行实现了：从“选座”界面跳转到了“确认订单，支付”界面
    $('#seat-state').css("display", "none");
    $('#order-state').css("display", "");
    //currentValue是当前元素的值，即为selectedSeats[i]
    //seatscon是一个Array数组，里面的元素是自定义的seatform，包含行列序号
    var seatscon=selectedSeats.map(function (currentValue) {
        var seatform={
            columnIndex:currentValue[1],
            rowIndex:currentValue[0]
        }
        return seatform;
    })
    console.log(seatscon)
    form = {
        userId: parseInt(sessionStorage.getItem('id')),
        scheduleId: scheduleId,
        seats: seatscon
    };
    //此处seats是array类型的
    console.log(form)
//    if(!validateForm(form)) {
//        return;
//    }
    //addTicket返回一个ticketVO类型的ticketList和一个total的double值
    //将TicketForm类型的值传给后端

    postRequest(
        '/ticket/lockSeat',
        form,
        function (res) {
            console.log(res.content);
            var orderInfo=res.content;//res.content是List<Ticket>

            console.log("======================================uuuuuuuuuuuuuuuuuuuu");
            console.log(orderInfo);
            for (let ticketInfo of orderInfo.ticketVOList) {
                TicketID[TicketID.length]=ticketInfo.id;
            }
            renderOrder(orderInfo);
        },
        function (error) {
            alert(error);
        }
    );

    // TODO:这里是假数据，需要连接后端获取真数据，数据格式可以自行修改，但如果改了格式，别忘了修改renderOrder方法

    getRequest(
        //sessionStorage 是一个前端的概念，
        // 它只是可以将一部分数据在当前会话中保存下来，刷新页面数据依旧存在。
        // 但当页面关闭后，sessionStorage 中的数据就会被清空。
        //sessionStorage 非常适合SPA(单页应用程序)，可以方便在各业务模块进行传值。
        //返回键名(key)对应的值(value)。
        //此处的id应为userId

        '/vip/' + sessionStorage.getItem('id') + '/get',
        function (res) {
            //res.success=true就说明该用户有会员卡
            //res.content是PO包中的VIPCard类型
            isVIP = res.success;
            useVIP = res.success;
            if (isVIP) {
                $('#member-balance').html("<div><b>会员卡余额：</b>" + res.content.balance.toFixed(2) + "元</div>");
            } else {
                //付款表单的上方付款方式选择中只有银行卡支付
                $("#member-pay").css("display", "none");
                //active：显示样式，表现出我们选择了它
                $("#nonmember-pay").addClass("active");

                $("#modal-body-member").css("display", "none");
                $("#modal-body-nonmember").css("display", "");
            }
        },
        function (error) {
            alert(error);
        });
}

//点击付款表单的上方付款方式选择时，触发此段代码。
//会员卡支付，type=0
//银行卡支付，type=1
function switchPay(type) {
    useVIP = (type === 0);
    if (type === 0) {
        $("#member-pay").addClass("active");
        $("#nonmember-pay").removeClass("active");

        $("#modal-body-member").css("display", "");
        $("#modal-body-nonmember").css("display", "none");
    } else {
        $("#member-pay").removeClass("active");
        $("#nonmember-pay").addClass("active");

        $("#modal-body-member").css("display", "none");
        $("#modal-body-nonmember").css("display", "");
    }
}
//TODO:数据格式修改之后，renderOrder方法也要修改
function renderOrder(orderInfo) {
    //修改“确认订单，支付”界面的表格中“票数/座位”一栏中的内容
    var ticketStr = "<div>" + selectedSeats.length + "张</div>";
    for (let ticketInfo of orderInfo.ticketVOList) {
        ticketStr += "<div>" + (ticketInfo.rowIndex + 1) + "排" + (ticketInfo.columnIndex + 1) + "座</div>";
        //order.ticketId是一个数组
        //push：向数组的末尾增加新元素
        order.ticketId.push(ticketInfo.id);
    }
    $('#order-tickets').html(ticketStr);

    //总金额，保留小数点后两位
    var total = orderInfo.total.toFixed(2);
    $('#order-total').text(total);
    $('#order-footer-total').text("总金额： ¥" + total);


    var couponTicketStr = "";
    //没有优惠券的情况
    if (orderInfo.coupons.length === 0) {
        $('#order-discount').text("优惠金额：无");
        $('#order-actual-total').text(" ¥" + total);
        $('#pay-amount').html("<div><b>金额：<movieName/b>" + total + "元</div>");
    } else {
        coupons = orderInfo.coupons;
        //coupon是CouponForm类型的
        console.log(coupons);
        for (let coupon of coupons) {
            //增加选项
            couponTicketStr += "<option>满" + coupon.targetAmount + "减" + coupon.discountAmount + "</option>"
        }
        $('#order-coupons').html(couponTicketStr);
        //优惠券选择列表中默认选择第一个
        changeCoupon(0);
    }
}

//情况1：在renderOrder中调用  情况2：当在优惠券选择列表中进行选择时，触发此段代码
function changeCoupon(couponIndex) {
    couponIndexNow=couponIndex;
    order.couponId = coupons[couponIndex].id;
    console.log(coupons[couponIndex].id);
    //保留两位小数
    $('#order-discount').text("优惠金额： ¥" + coupons[couponIndex].discountAmount.toFixed(2));
    var actualTotal = (parseFloat($('#order-total').text()) - parseFloat(coupons[couponIndex].discountAmount)).toFixed(2);
    $('#order-actual-total').text(" ¥" + actualTotal);
    $('#pay-amount').html("<div><b>金额：</b>" + actualTotal + "元</div>");
}

//当点击“支付成功”界面中的确认支付按钮时，触发此段代码
function payConfirmClick() {
    var payOrder=[];
    payOrder[0]=TicketID;
    payOrder[1]=couponIndexNow;
    var postData={
        ticketIdList:TicketID,
        couponId:order.couponId
    }
    if (useVIP) {
        console.log(payOrder);
        postRequest(
            '/ticket/vip/buy',
            postData,
            function (res) {
                console.log("================");
                console.log(res);
                if(res.success){
                    postPayRequest();
                }else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            }
        );

    } else {
        if (validateForm()) {
            if ($('#userBuy-cardNum').val() === "123123123" && $('#userBuy-cardPwd').val() === "123123") {
                console.log(payOrder);
                postRequest(
                    '/ticket/buy',
                    postData,
                    function (res) {
                        console.log("================");
                        console.log(res);
                        postPayRequest();
                    },
                    function (error) {
                        alert(error);
                    }
                );
            } else {
                alert("银行卡号或密码错误");
            }
        }
    }
}

// TODO:填空
function postPayRequest() {
    //从“确认订单，支付”界面切换到“支付成功界面”
    $('#order-state').css("display", "none");
    $('#success-state').css("display", "");
    //付款的表单隐藏
    $('#buyModal').modal('hide');
}

//进行表单验证
function validateForm() {
    var isValidate = true;
    if (!$('#userBuy-cardNum').val()) {
        isValidate = false;
        $('#userBuy-cardNum').parent('.form-group').addClass('has-error');
        $('#userBuy-cardNum-error').css("visibility", "visible");
    }
    if (!$('#userBuy-cardPwd').val()) {
        isValidate = false;
        $('#userBuy-cardPwd').parent('.form-group').addClass('has-error');
        $('#userBuy-cardPwd-error').css("visibility", "visible");
    }
    return isValidate;
}