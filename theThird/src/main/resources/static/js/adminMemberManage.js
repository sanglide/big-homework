$(document).ready(function () {

    getVIPCardInfo();

    function getVIPCardInfo(){
        getRequest(
            '/vip/getVIPInfo',
            function (res) {
                console.log("ssssssssssssssssss");
                console.log(res);
                if (res.success) {
                    console.log(res.content);
                    //res.content装的是满足金额target,优惠金额discount,本卡价格price
                    $("#VIPCard-price").text(res.content.price);
                    $("#VIPCard-target").text(res.content.charge);
                    $("#VIPCard-discount").text(res.content.bonus);

                    // $("#VIPCard-description").text("满"+res.content.charge+"送"+res.content.bonus);
                    //$("#VIPCard-description").text(res.content.description);
                    // var des=res.content.description;
                    // var index1=des.indexOf("满");
                    // var index2=des.indexOf("送");
                    // var target=des.slice(index1+1,index2);//是满足金额
                    // var discount=des.slice(index2+1);//是优惠金额
                    // console.log(target);
                    // console.log(discount);
                    $("#VIPCard-price-input").val(res.content.price);
                    // $("#VIPCard-target-input").val(target);
                    // $("#VIPCard-discount-input").val(discount);
                    $("#VIPCard-target-input").val(res.content.charge);
                    $("#VIPCard-discount-input").val(res.content.bonus);
                } else {
                    alert(res.content);
                }

            },
            function (error) {
                alert(error);
            });
    }

    $("#VIPCard-form-btn").click(function () {
        var form = {
            price: Number($("#VIPCard-price-input").val()),
            charge: Number($("#VIPCard-target-input").val()),
            bonus: Number($("#VIPCard-discount-input").val())
        }

        if (!validateVIPCardEditForm(form)) {
            return;
        }

        // var finalForm={
        //     price:form.price,
        //     description:"满"+form.target+"送"+form.discount
        // }

        // console.log(finalForm);

        console.log(form);
        postRequest(
            '/vip/update',
            // finalForm,
            form,
            function (res) {
                if (res.success) {
                    getVIPCardInfo();
                    $("#VIPCardModal").modal('hide');
                    alert("修改会员卡策略成功！")
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        )
    })
    function validateVIPCardEditForm(data){
        var isValidate=true;
        function isAValidNum(f){
            var isValid=false;
            if(/^[0-9]+$/.test(f)||/^[0-9]+\.?[0-9]+?$/.test(f)){
                isValid=true;
            }
            return isValid;
        }
        if(!data.price||data.price<0||!isAValidNum(data.price)){
            //不存在或者为负数或者(不是整数且不是小数)
            isValidate=false;
            $('#VIPCard-price-error').css("visibility", "visible");
            $("#VIPCard-price-error").text("请正确输入非负整数或非负小数");
            //showErrorMessage()
        }
        if(!data.charge||data.charge<0||!isAValidNum(data.charge)){
            //不存在或者为负数或者(不是整数且不是小数)
            isValidate=false;
            $('#VIPCard-target-error').css("visibility", "visible");
            $("#VIPCard-target-error").text("请正确输入非负整数或非负小数");
            //showErrorMessage()
        }
        if(!data.bonus||data.bonus<0||!isAValidNum(data.bonus)){
            //不存在或者为负数或者(不是整数且不是小数)
            isValidate=false;
            $('#VIPCard-discount-error').css("visibility", "visible");
            $("#VIPCard-discount-error").text("请正确输入非负整数或非负小数");
            //showErrorMessage()
        }
        // if(data.charge<data.bonus){
        //     isValidate=false;
        //     $('#VIPCard-target-error').css("visibility", "visible");
        //     $("#VIPCard-target-error").text("满足金额必须大于优惠金额");
        // }
        return isValidate;
    }


});
