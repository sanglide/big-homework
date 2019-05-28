$(document).ready(function () {

    getVIPCardInfo();
//
    function getVIPCardInfo(){
        getRequest(
            '/vip/getVIPInfo',
            function (res) {
                if (res.success) {
                    console.log(res);
                    $("#VIPCard-price").text(res.content.price);
                    $("#VIPCard-description").text(res.content.description);
                    var des=res.content.description;
                    var index1=des.indexOf("满");
                    var index2=des.indexOf("送");
                    var target=des.slice(index1+1,index2);//是满足金额
                    var discount=des.slice(index2+1);//是优惠金额
                    console.log(target);
                    console.log(discount);
                    $("#VIPCard-price-input").val(res.content.price);
                    $("#VIPCard-target-input").val(target);
                    $("#VIPCard-discount-input").val(discount);
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
            price: $("#VIPCard-price-input").val(),
            target: $("#VIPCard-target-input").val(),
            discount: $("#VIPCard-discount-input").val()
        }

        if (!validateVIPCardEditForm(form)) {
            return;
        }

        var finalForm={
            price:form.price,
            description:"满"+form.target+"送"+form.discount
        }

        console.log(finalForm);

        postRequest(
            'vip/update',
            finalForm,
            function (res) {
                if (res.success) {
                    getVIPCardInfo();
                    $("#VIPCardModal").modal('hide');
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
        if(!data.target||data.target<0||!isAValidNum(data.target)){
            //不存在或者为负数或者(不是整数且不是小数)
            isValidate=false;
            $('#VIPCard-target-error').css("visibility", "visible");
            $("#VIPCard-target-error").text("请正确输入非负整数或非负小数");
            //showErrorMessage()
        }
        if(!data.discount||data.discount<0||!isAValidNum(data.discount)){
            //不存在或者为负数或者(不是整数且不是小数)
            isValidate=false;
            $('#VIPCard-discount-error').css("visibility", "visible");
            $("#VIPCard-discount-error").text("请正确输入非负整数或非负小数");
            //showErrorMessage()
        }
        if(data.target<data.discount){
            isValidate=false;
            $('#VIPCard-target-error').css("visibility", "visible");
            $("#VIPCard-target-error").text("满足金额必须大于优惠金额");
        }
        return isValidate;
    }


});
