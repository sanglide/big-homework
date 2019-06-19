$(document).ready(function(){//页面一加载好后就执行该匿名函数

    var movieId = parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);
    var userId = sessionStorage.getItem('id');
    var isLike = false;

    getMovie();
    if(sessionStorage.getItem('role') === 'admin')//严格等于
        getMovieLikeChart();//获取想看人数的图表

    function getMovieLikeChart() {
       getRequest(
           '/movie/' + movieId + '/like/date',
           function(res){
               var data = res.content,
                    dateArray = [],
                    numberArray = [];
               data.forEach(function (item) {
                   dateArray.push(item.likeTime);
                   numberArray.push(item.likeNum);
               });

               var myChart = echarts.init($("#like-date-chart")[0]);

               // 指定图表的配置项和数据
               var option = {
                   title: {
                       text: '想看人数变化表'
                   },
                   xAxis: {
                       type: 'category',
                       data: dateArray
                   },
                   yAxis: {
                       type: 'value'
                   },
                   series: [{
                       data: numberArray,
                       type: 'line'
                   }]
               };

               // 使用刚指定的配置项和数据显示图表。
               myChart.setOption(option);
           },
           function (error) {
               alert(error);
           }
       );
    }

    function getMovie() {
        getRequest(
            '/movie/'+movieId + '/' + userId,
            function(res){
                var data = res.content;//data是MovieVO
                isLike = data.islike;//是否想看
                repaintMovieDetail(data);
            },
            function (error) {
                alert(error);
            }
        );
    }

    //获取最新的电影细节信息,更新页面上的电影细节信息
    //movie是MovieVO类型的
    function repaintMovieDetail(movie) {
        //“想看”中的爱心图标，如果不想看，就移除class,如果想看，就增加class
        !isLike ? $('.icon-heart').removeClass('error-text') : $('.icon-heart').addClass('error-text');
        //如果想看，就文字替换为“已想看”，如果还没有想看，点击按钮之后，文字就显示为“想 看”
        $('#like-btn span').text(isLike ? ' 已想看' : ' 想 看');
        //设置属性，电影的海报
        $('#movie-img').attr('src',movie.posterUrl);
        $('#movie-name').text(movie.name);
        $('#order-movie-name').text(movie.name);
        $('#movie-description').text(movie.description);
        $('#movie-startDate').text(new Date(movie.startDate).toLocaleDateString());
        $('#movie-type').text(movie.type);
        $('#movie-country').text(movie.country);
        $('#movie-language').text(movie.language);
        $('#movie-director').text(movie.director);
        $('#movie-starring').text(movie.starring);
        $('#movie-writer').text(movie.screenWriter);
        $('#movie-length').text(movie.length);
        if(movie.status==1){
            //电影为下架状态,则按钮不能用
            $("#modify-btn").attr("disabled",true);
            $("#delete-btn").attr("disabled",true);
        }
    }

    // user界面才有
    $('#like-btn').click(function () {//客户点击“想看”按钮时会触发
        var url = isLike ?'/movie/'+ movieId +'/unlike?userId='+ userId :'/movie/'+ movieId +'/like?userId='+ userId;
        postRequest(
             url,
            null,
            function (res) {
                 isLike = !isLike;
                getMovie();
            },
            function (error) {
                alert(error);
            });
    });

    // admin界面才有
    $("#modify-btn").click(function () {//修改电影
        //表单中初始内容为当前页面的电影的信息
        getRequest(
            '/movie/'+movieId + '/' + userId,
            function(res){
                var data = res.content;//data是一个MovieVO对象
                initMovieEditForm(data);
            },
            function (error) {
                alert(error);
            }
        );
       //alert('交给你们啦，修改时需要在对应html文件添加表单然后获取用户输入，提交给后端，别忘记对用户输入进行验证。（可参照添加电影&添加排片&修改排片）');
    });

    $("#delete-btn").click(function () {//下架电影
        //确认提示框
        var r=confirm("确认要下架该电影吗");
        if(r){
            postRequest(
                "/movie/off/batch",
                {movieIdList:[movieId]},
                function(res){
                    if(res.success){
                        //还要更改该电影的class，对用户进行提示！！！
                        alert("下架电影成功");
                        getMovie();
                    }else{
                        alert((res.message()))
                    }
                },
                function (error) {
                alert(JSON.stringify(error));
            }
            )
        }
        //alert('交给你们啦，下架别忘记需要一个确认提示框，也别忘记下架之后要对用户有所提示哦');
    });

    function initMovieEditForm(movie){
        $("#movie-edit-name-input").val(movie.name);
        $("#movie-edit-date-input").val(movie.startDate.slice(0,10));
        $("#movie-edit-img-input").val(movie.posterUrl);
        $("#movie-edit-description-input").val(movie.description);
        $("#movie-edit-type-input").val(movie.type);
        $("#movie-edit-length-input").val(movie.length);
        $("#movie-edit-country-input").val(movie.country);
        $("#movie-edit-language-input").val(movie.language);
        $("#movie-edit-director-input").val(movie.director);
        $("#movie-edit-star-input").val(movie.starring);
        $("#movie-edit-writer-input").val(movie.screenWriter);

        $("#movieEditModal").modal('show');
    }

    //按下修改电影的确认按钮后
    //click没有定义！！！
    $("#movie-edit-form-btn").click(function(){
        var form = {
            id: movieId,
            name: $("#movie-edit-name-input").val(),
            posterUrl: $("#movie-edit-img-input").val(),
            director: $("#movie-edit-director-input").val(),
            screenWriter: $("#movie-edit-writer-input").val(),
            starring: $("#movie-edit-star-input").val(),
            type: $("#movie-edit-type-input").val(),
            country: $("#movie-edit-country-input").val(),
            language: $("#movie-edit-language-input").val(),
            startDate: $("#movie-edit-date-input").val(),
            length: $("#movie-edit-length-input").val(),
            description: $("#movie-edit-description-input").val(),
        };
        console.log(form)
        //验证表单
        if(!validateMovieEditForm(form)){
            return;
        }
        //向服务器发送更新的电影信息
        postRequest(
            '/movie/update',
            form,
            function (res) {//传递的是MovieForm类型的数据
                getMovie();
                $("#movieEditModal").modal('hide');//表单隐藏
               },
            function (error) {
                alert(error);
            }
        );
    })

    //验证表单
    function validateMovieEditForm(data) {
        var isValidate = true;
        if(!data.name) {
            isValidate = false;
            $('#movie-name-input').parent('.form-group').addClass('has-error');
        }
        if(!data.posterUrl) {
            isValidate = false;
            $('#movie-img-input').parent('.form-group').addClass('has-error');
        }
        if(!data.startDate) {
            isValidate = false;
            $('#movie-date-input').parent('.form-group').addClass('has-error');
        }
        return isValidate;
    }

});