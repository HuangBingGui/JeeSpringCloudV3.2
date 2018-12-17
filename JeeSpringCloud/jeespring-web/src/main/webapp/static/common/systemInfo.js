$(function() {
	// <!--Step:1 为ECharts准备一个具备大小（宽高）的Dom-->
	// Step:3 echarts & zrender as a Global Interface by the echarts-plain.js.
	// Step:3 echarts和zrender被echarts-plain.js写入为全局接口
	//onloadurl();
	var locat = (window.location+'').split('/'); 
	if('tool'== locat[3]){locat =  locat[0]+'//'+locat[2];}else{locat =  locat[0]+'//'+locat[2]+'/'+locat[3];};
    //初始化实际监控数据
	var myChart = echarts.init(document.getElementById('main'));
    var myChartSecond = echarts.init(document.getElementById('mainSecond'));
    var myChartMin = echarts.init(document.getElementById('mainMin'));
    var myChartHour = echarts.init(document.getElementById('mainHour'));
	var now = new Date();
	var res = [];
    var resMin = [];
    var resHour = [];
    var dataSecendJVM=[];
    var dataSecendRAM=[];
    var dataSecendCPU=[];
    var dataMinJVM=[];
    var dataMinRAM=[];
    var dataMinCPU=[];
    var dataHourJVM=[];
    var dataHourRAM=[];
    var dataHourCPU=[];
    $.ajax({
        type : "POST",
        url : locat + '/monitor/usage',
        async : false,
        dataType : 'json',
        success : function(json) {
            var len = 20;
            while (len--) {
                var time = now.toLocaleTimeString().replace(/^\D*/, '');
                time = time.substr(time.indexOf(":") + 1);
                res.unshift(now.getHours()+":"+time);
                dataSecendJVM.push( json.jvmUsage+Math.ceil(Math.random()*5));
                dataSecendRAM.push(json.ramUsage+Math.ceil(Math.random()*5));
                dataSecendCPU.push(json.cpuUsage+Math.ceil(Math.random()*5));
                now = new Date(now - 1000);
            }
            var lenMin = 60;
            now = new Date();
            while (lenMin--) {
                var time = now.getHours()+":"+now.getMinutes();
                resMin.unshift(time);
                dataMinJVM.push( json.jvmUsage+Math.ceil(Math.random()*5));
                dataMinRAM.push(json.ramUsage+Math.ceil(Math.random()*5));
                dataMinCPU.push(json.cpuUsage+Math.ceil(Math.random()*5));
                now = new Date(now - 60000);
            }
            var lenHour = 24;
            now = new Date();
            while (lenHour--) {
                var time = now.getHours()+":"+now.getMinutes();
                resHour.unshift(time);
                dataHourJVM.push( json.jvmUsage+Math.ceil(Math.random()*5));
                dataHourRAM.push(json.ramUsage+Math.ceil(Math.random()*5));
                dataHourCPU.push(json.cpuUsage+Math.ceil(Math.random()*5));
                now = new Date(now - 3600000);
            }
        },error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.status);
            alert(XMLHttpRequest.readyState);
            alert(textStatus);
        }
    });
	option = {
        title : {
            text: '每秒',
            //subtext: '每秒'
        },
        legend: {
            data:['最高','最低']
        },
        calculable : true,
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
		legend : {
			data : [ 'jvm内存使用率', '物理内存使用率', 'cpu使用率' ]
		},
		grid : {
			x : 40,
			y : 30,
			x2 : 10,
			y2 : 35,
			borderWidth : 0,
			borderColor : "#FFFFFF"
		},
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
		xAxis : [ {
			axisLabel : {
				rotate : 20,
			},
			type : 'category',// 坐标轴类型，横轴默认为类目型'category'，纵轴默认为数值型'value'
			data : res
		} ],
		yAxis : [ {
			min : 0,
			max : 100,
			axisLabel : {
				formatter : '{value}%'
			}
		} ],
		series : [
				{
					name : 'jvm内存使用率',
					type : 'line',
					data : dataSecendJVM
				},
				{
					name : '物理内存使用率',
					type : 'line',
					data :dataSecendRAM
				},
				{
					name : 'cpu使用率',
					type : 'line',
					data : dataSecendCPU
				} ]
	};
    optionSecond = {
        title : {
            text: '每秒',
            //subtext: '每秒'
        },
        legend: {
            data:['最高','最低']
        },
        calculable : true,
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend : {
            data : [ 'jvm内存使用率', '物理内存使用率', 'cpu使用率' ]
        },
        grid : {
            x : 40,
            y : 30,
            x2 : 10,
            y2 : 35,
            borderWidth : 0,
            borderColor : "#FFFFFF"
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        xAxis : [ {
            axisLabel : {
                rotate : 20,
            },
            type : 'category',// 坐标轴类型，横轴默认为类目型'category'，纵轴默认为数值型'value'
            data : [res[0]]
        } ],
        yAxis : [ {
            min : 0,
            max : 100,
            axisLabel : {
                formatter : '{value}%'
            }
        } ],
        series : [
            {
                name : 'jvm内存使用率',
                type : 'line',
                data : [dataSecendJVM[0]]
            },
            {
                name : '物理内存使用率',
                type : 'line',
                data :[dataSecendRAM[0]]
            },
            {
                name : 'cpu使用率',
                type : 'line',
                data : [dataSecendCPU[0]]
            } ]
    };
    optionMin = {
        title : {
            text: '每分钟',
            //subtext: ''
        },
        legend: {
            data:['最高','最低']
        },
        calculable : true,
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend : {
            data : [ 'jvm内存使用率', '物理内存使用率', 'cpu使用率' ]
        },
        grid : {
            x : 40,
            y : 30,
            x2 : 10,
            y2 : 35,
            borderWidth : 0,
            borderColor : "#FFFFFF"
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        xAxis : [ {
            axisLabel : {
                rotate : 20,
            },
            type : 'category',// 坐标轴类型，横轴默认为类目型'category'，纵轴默认为数值型'value'
            data : resMin
        } ],
        yAxis : [ {
            min : 0,
            max : 100,
            axisLabel : {
                formatter : '{value}%'
            }
        } ],
        series : [
            {
                name : 'jvm内存使用率',
                type : 'line',
                data :dataMinJVM
            },
            {
                name : '物理内存使用率',
                type : 'line',
                data :dataMinRAM
            },
            {
                name : 'cpu使用率',
                type : 'line',
                data : dataMinCPU
            } ]
    };
    optionHour = {
        title : {
            text: '每小时',
            //subtext: '每小时'
        },
        legend: {
            data:['最高','最低']
        },
        calculable : true,
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend : {
            data : [ 'jvm内存使用率', '物理内存使用率', 'cpu使用率' ]
        },
        grid : {
            x : 40,
            y : 30,
            x2 : 10,
            y2 : 35,
            borderWidth : 0,
            borderColor : "#FFFFFF"
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        xAxis : [ {
            axisLabel : {
                rotate : 20,
            },
            type : 'category',// 坐标轴类型，横轴默认为类目型'category'，纵轴默认为数值型'value'
            data : resHour
        } ],
        yAxis : [ {
            min : 0,
            max : 100,
            axisLabel : {
                formatter : '{value}%'
            }
        } ],
        series : [
            {
                name : 'jvm内存使用率',
                type : 'line',
                data : dataHourJVM,
                markPoint : {
                    data : [
                        {type : 'max', name: '最大值'},
                        {type : 'min', name: '最小值'}
                    ]
                },
                markLine : {
                    data : [
                        {type : 'average', name: '平均值'}
                    ]
                }
            },
            {
                name : '物理内存使用率',
                type : 'line',
                data : dataHourRAM,
                markPoint : {
                    data : [
                        {type : 'max', name: '最大值'},
                        {type : 'min', name: '最小值'}
                    ]
                },
                markLine : {
                    data : [
                        {type : 'average', name: '平均值'}
                    ]
                }
            },
            {
                name : 'cpu使用率',
                type : 'line',
                data : dataHourCPU,
                markPoint : {
                    data : [
                        {type : 'max', name: '最大值'},
                        {type : 'min', name: '最小值'}
                    ]
                },
                markLine : {
                    data : [
                        {type : 'average', name: '平均值'}
                    ]
                }
            } ]
    };
	myChart.setOption(option);
    myChartSecond.setOption(optionSecond);
    myChartMin.setOption(optionMin);
    myChartHour.setOption(optionHour);

    //初始化仪表数据
	var main_one = echarts.init(document.getElementById('main_one'));
	var main_two = echarts.init(document.getElementById('main_two'));
	var main_three = echarts.init(document.getElementById('main_three'));
	one_option = {
		    tooltip : {
		        formatter: "{a} <br/>{b} : {c}%"
		    },
		    series : [
		        {
		        	title:{
		        	    show : true,
		        	    offsetCenter: [0, "95%"],
		        	 },
		        	 pointer: {
		                 color: '#FF0000'
		             },
		             name:'监控指标',
		            radius:[0, '95%'],
		            axisLine: {            // 坐标轴线
		                lineStyle: {       // 属性lineStyle控制线条样式
		                    width: 20
		                }
		            },
		            detail : {formatter:'{value}%'},
		            type:'gauge',
		            data:[{value: 50, name: 'JVM使用率'}]
		        }
		    ]
		};
    two_option = {
			 tooltip : {
			        formatter: "{a} <br/>{b} : {c}%"
			    },
			    series : [
			        {
			            name:'监控指标',
			            type:'gauge',
			            startAngle: 180,
			            endAngle: 0,
			            center : ['50%', '90%'],    // 默认全局居中
			            radius : 180,
			            axisLine: {            // 坐标轴线
			                lineStyle: {       // 属性lineStyle控制线条样式
			                    width: 140
			                }
			            },
			            axisTick: {            // 坐标轴小标记
			                splitNumber: 10,   // 每份split细分多少段
			                length :12,        // 属性length控制线长
			            },
			            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
			               
			                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
			                    color: '#fff',
			                    fontSize: 15,
			                    fontWeight: 'bolder'
			                }
			            },
			          
			            pointer: {
			                width:10,
			                length: '80%',
			                color: 'rgba(255, 255, 255, 0.8)'
			            },
			            title : {
			                show : true,
			                offsetCenter: [0, 15],       // x, y，单位px
			               /* textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
			                    color: '#fff',
			                    fontSize: 25
			                }*/
			            },
			            detail : {
			                show : true,
			                backgroundColor: 'rgba(0,0,0,0)',
			                borderWidth: 0,
			                borderColor: '#ccc',
			                offsetCenter: [0, -40],       // x, y，单位px
			                formatter:'{value}%',
			                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
			                    fontSize : 20
			                }
			            },
			            data:[{value: 50, name: 'cpu使用率'}]
			        }
			    ]
			};
	 main_one.setOption(one_option);
     main_two.setOption(two_option);
	 one_option.series[0].data[0].name ='内存使用率';
	 one_option.series[0].pointer.color='#428bca'
	 main_three.setOption(one_option);

	//定时刷新
    var axisData;
	clearInterval(timeTicket);
    clearInterval(timeTicketMin);
    clearInterval(timeTicketHour);
	var timeTicket = setInterval(function() {
		axisData = (new Date()).toLocaleTimeString().replace(/^\D*/, '');
		axisData = now.getHours()+":"+axisData.substr(axisData.indexOf(":") + 1);
		var jvm = [];
		var ram = [];
		var cpu = [];
		$.ajax({
			type : "POST",
			url : locat + '/monitor/usage',
			/*async : false,*/
			dataType : 'json',
			success : function(json) {
				$("#td_jvmUsage").html(json.jvmUsage);
				$("#td_serverUsage").html(json.ramUsage);
				$("#td_cpuUsage").html(json.cpuUsage);
				
				
				jvm.push(json.jvmUsage);
				ram.push(json.ramUsage);
				cpu.push(json.cpuUsage);
				// 动态数据接口 addData
				myChart.addData([ [ 0, // 系列索引
				jvm, // 新增数据
				false, // 新增数据是否从队列头部插入
				false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
				], [ 1, // 系列索引
				ram, // 新增数据
				false, // 新增数据是否从队列头部插入
				false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
				], [ 2, // 系列索引
				cpu, // 新增数据
				false, // 新增数据是否从队列头部插入
				false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
				axisData // 坐标轴标签
				] ]);

                myChartSecond.addData([ [ 0, // 系列索引
                    jvm, // 新增数据
                    false, // 新增数据是否从队列头部插入
                    true, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
                ], [ 1, // 系列索引
                    ram, // 新增数据
                    false, // 新增数据是否从队列头部插入
                    true, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
                ], [ 2, // 系列索引
                    cpu, // 新增数据
                    false, // 新增数据是否从队列头部插入
                    true, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
                    axisData // 坐标轴标签
                ] ]);

				one_option.series[0].data[0].value =json.jvmUsage;
				one_option.series[0].data[0].name ='JVM使用率';
				 one_option.series[0].pointer.color='#FF0000'
				main_one.setOption(one_option, true);
				
				two_option.series[0].data[0].value =json.cpuUsage;
                main_two.setOption(two_option, true);

				one_option.series[0].data[0].value =json.ramUsage;
				 one_option.series[0].data[0].name ='内存使用率';
				 one_option.series[0].pointer.color='#428bca'
				main_three.setOption(one_option, true);
			}
		});
	}, 10000);
    var timeTicketMin = setInterval(function() {
        now=new Date();
        axisData =  now.getHours()+":"+now.getMinutes();
        var jvm = [];
        var ram = [];
        var cpu = [];
        $.ajax({
            type : "POST",
            url : locat + '/monitor/usage',
            /*async : false,*/
            dataType : 'json',
            success : function(json) {
                jvm.push(json.jvmUsage);
                ram.push(json.ramUsage);
                cpu.push(json.cpuUsage);
                // 动态数据接口 addData
                myChartMin.addData([ [ 0, // 系列索引
                    jvm, // 新增数据
                    false, // 新增数据是否从队列头部插入
                    false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
                ], [ 1, // 系列索引
                    ram, // 新增数据
                    false, // 新增数据是否从队列头部插入
                    false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
                ], [ 2, // 系列索引
                    cpu, // 新增数据
                    false, // 新增数据是否从队列头部插入
                    false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
                    axisData // 坐标轴标签
                ] ]);
            }
        });
    }, 60000);
    var timeTicketHour = setInterval(function() {
        now=new Date();
        axisData =  now.getHours()+":"+now.getMinutes();
        var jvm = [];
        var ram = [];
        var cpu = [];
        $.ajax({
            type : "POST",
            url : locat + '/monitor/usage',
            /*async : false,*/
            dataType : 'json',
            success : function(json) {
                jvm.push(json.jvmUsage);
                ram.push(json.ramUsage);
                cpu.push(json.cpuUsage);
                // 动态数据接口 addData
                myChartHour.addData([ [ 0, // 系列索引
                    jvm, // 新增数据
                    false, // 新增数据是否从队列头部插入
                    false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
                ], [ 1, // 系列索引
                    ram, // 新增数据
                    false, // 新增数据是否从队列头部插入
                    false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
                ], [ 2, // 系列索引
                    cpu, // 新增数据
                    false, // 新增数据是否从队列头部插入
                    false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
                    axisData // 坐标轴标签
                ] ]);
            }
        });
    }, 360000);
});