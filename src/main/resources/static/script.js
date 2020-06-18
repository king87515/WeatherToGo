var place_data=[
  {
   tag: "taipei_city",
   place: "臺北市"
  },

  {
   tag: "new_taipei_city",
   place: "新北市"
  },

  {
   tag: "taichung_city",
   place: "臺中市"
  },

  {
   tag: "tainan_city",
   place: "臺南市"
  },

  {
   tag: "kaohsiung_city",
   place: "高雄市"
  },

  {
   tag: "keelung_city",
   place: "基隆市"
  },

  {
   tag: "taoyuan_country",
   place: "桃園市"
  },

  {
   tag: "hsinchu_city",
   place: "新竹市"
  },

  {
   tag: "hsinchu_country",
   place: "新竹縣"
  },

  {
   tag: "miaoli_country",
   place: "苗栗縣"
  },

  {
   tag: "changhua_country",
   place: "彰化縣"
  },

  {
   tag: "nantou_country",
   place: "南投縣"
  },

  {
   tag: "yunlin_country",
   place: "雲林縣"
  },

  {
   tag: "chiayi_city",
   place: "嘉義市"
  },

  {
   tag: "chiayi_country",
   place: "嘉義縣"
  },

  {
   tag: "pingtung_country",
   place: "屏東縣"
  },

  {
   tag: "yilan_country",
   place: "宜蘭縣"
  },

  {
   tag: "hualien_country",
   place: "花蓮縣"
  },

  {
   tag: "taitung_country",
   place: "臺東縣"
  },

  {
   tag: "penghu_country",
   place: "澎湖縣"
  },

  {
   tag: "kinmen_country",
   place: "金門縣"
  },

  {
   tag: "lienchiang_country",
   place: "連江縣"
  },
]
;

var vm = new Vue({
  el: "#app",
  data: {
    filter: "",
    place_data: place_data
  },computed:{
    now_area: function(){
      var vobj=this;
      var result=this.place_data.filter(function(obj){
        return obj.tag==vobj.filter;
      });
      if (result.length==0){
        return null;
      }
      return result[0];
    }
  }
});

$("path").mouseenter(function(e){
  $("#des").css('display', 'none');
  var tagname=$(this).attr("data-name");
  vm.filter=tagname;
//  console.log(vm.now_area.place);
  $(".forcast h3").show();
  $(".forcast h4").show();
  getWeather(vm.now_area.place);
});

$("path").mouseleave(function(e){
	$("#des").css('display', 'none');
	$(".forcast h3").hide();
	$(".forcast h4").hide();
	$("#menu tr").remove();
});

$("path").mousedown(function(e){
	$("#des").css('display', 'none');
	getInfo(vm.now_area.place);
});

function getWeather(place){
	var content;
	$("#menu tr").remove();
	let jsonUrl = "location/"+place;
//	console.log("jsonUrl: "+jsonUrl);
	
	$.getJSON(jsonUrl, function(data) {
//		console.log(data);
//		console.log(data.note);

	    content =
	       	"<tr><th>-----</th><th>今晚明晨</th><th>明天白天</th><th>明日晚上</th></tr>"
       	 	+ "<tr>" 
       		+ "<td>天氣現象</td>" 
       		+ "<td>" + data.weatherElement[0].time[0].parameter.parameterName + "</td>" 
       		+ "<td>" + data.weatherElement[0].time[1].parameter.parameterName + "</td>" 
       		+ "<td>" + data.weatherElement[0].time[2].parameter.parameterName + "</td>"
       		+ "</tr>"
       		+ "<tr>" 
       		+ "<td>最高溫度</td>" 
       		+ "<td>" + data.weatherElement[1].time[0].parameter.parameterName + "&#176C</td>" 
       		+ "<td>" + data.weatherElement[1].time[1].parameter.parameterName + "&#176C</td>" 
       		+ "<td>" + data.weatherElement[1].time[2].parameter.parameterName + "&#176C</td>"
       		+ "</tr>"
       		+ "<tr>" 
       		+ "<td>最低溫度</td>" 
       		+ "<td>" + data.weatherElement[2].time[0].parameter.parameterName + "&#176C</td>" 
       		+ "<td>" + data.weatherElement[2].time[1].parameter.parameterName + "&#176C</td>" 
       		+ "<td>" + data.weatherElement[2].time[2].parameter.parameterName + "&#176C</td>"
       		+ "</tr>"
       		+ "<tr>" 
       		+ "<td>舒適度</td>" 
       		+ "<td>" + data.weatherElement[3].time[0].parameter.parameterName + "</td>" 
       		+ "<td>" + data.weatherElement[3].time[1].parameter.parameterName + "</td>" 
       		+ "<td>" + data.weatherElement[3].time[2].parameter.parameterName + "</td>"
       		+ "</tr>"
       		+ "<tr>" 
       		+ "<td>降雨機率</td>" 
       		+ "<td>" + data.weatherElement[4].time[0].parameter.parameterName + "%</td>" 
       		+ "<td>" + data.weatherElement[4].time[1].parameter.parameterName + "%</td>" 
       		+ "<td>" + data.weatherElement[4].time[2].parameter.parameterName + "%</td>"
       		+ "</tr>";
		$("#menu").append(content);


	});
}
function getInfo(place){
	var content;
	$("#info tr").remove();
	let jsonUrl = "info?address="+place;
	console.log("jsonUrl: "+jsonUrl);
	$("#info").append("<tr id='info_head'><th>Name</th><th>Address</th><th>Phone</th><th>Note</th><th>Create</th><th>Update</th><th>Delete</th></tr>");
	$.getJSON(jsonUrl, function(data) {
		console.log(data);
//		console.log(data[0]);
		
		for (let item in data) {
	    	if(data[item].note==="null"){
	    		data[item].note = null;
	    	}
	        content =
        	 	  "<tr id='value'  onclick=getDes(des_"+data[item].id+")>" 
         		+ "<td>" + data[item].name + "</td>" 
         		+ "<td>" + data[item].add + "</td>" 
         		+ "<td id='des_" + data[item].id + "' style='display:none;'>" + data[item].description + "</td>" 
         		+ "<td>" + data[item].tel + "</td>"
         		+ "<td id=" + data[item].id + ">" + data[item].note + "</td>" 
         		+ "<td><button type='button' onclick=New(" + data[item].id + ")>New</button></td>"
         		+ "<td><button type='button' onclick=Edit(" + data[item].id + ")>Edit</button></td>"
         		+ "<td><button type='button' onclick=Delete(" + data[item].id + ")>Delete</button></td>"
         		+ "</tr>";
			$("#info").append(content);
		}

	});
}

function getDes(id){
	$("#des").css('display', 'block');
	console.log(id);
	var description = document.getElementById("des");
	var description_value = id.innerText;
//	console.log(description);
//	console.log(description_value);
	if(description_value===""){
		 description_value="目前尚無描述唷！"
	}
	description.innerHTML = description_value;
}

function getInfoSearch(){
	var content;
	$("#info tr").remove();
	let address = document.getElementById("address").value;
	let name = document.getElementById("name").value;
	let jsonUrl = "info?address="+address+"&name="+name;
	console.log("jsonUrl: "+jsonUrl);
	$("#info").append("<tr id='info_head'><th>Name</th><th>Address</th><th>Phone</th><th>Note</th><th>Create</th><th>Update</th><th>Delete</th></tr>");
	$.getJSON(jsonUrl, function(data) {
		console.log(data);
//		console.log(data[0]);

		for (let item in data) {
	    	if(data[item].note==="null"){
	    		data[item].note = null;
	    	}
	        content =
	        	  "<tr id='value'  onclick=getDes(des_"+data[item].id+")>" 
         		+ "<td>" + data[item].name + "</td>" 
         		+ "<td>" + data[item].add + "</td>" 
         		+ "<td id='des_" + data[item].id + "' style='display:none;'>" + data[item].description + "</td>" 
         		+ "<td>" + data[item].tel + "</td>"
         		+ "<td id=" + data[item].id + ">" + data[item].note + "</td>" 
         		+ "<td><button type='button' onclick=New(" + data[item].id + ")>New</button></td>"
         		+ "<td><button type='button' onclick=Edit(" + data[item].id + ")>Edit</button></td>"
         		+ "<td><button type='button' onclick=Delete(" + data[item].id + ")>Delete</button></td>"
         		+ "</tr>";
			$("#info").append(content);
		}

	});
}

function New(id){
	//console.log(id.id);
	let noteContent = document.getElementById("note").value;
//	console.log(ID);
	console.log(noteContent);
	$.ajax({
		url: "/info/"+id.id+"/note",
		type: "POST",
		contentType: "text/plain", //傳過去的內容
		/* dataType : "text", */ //回傳的內容
		data : noteContent, 
		success : function (data) {
			console.log(data);
			id.innerHTML = noteContent;
		}
		
	})
}
function Edit(id){
	let noteContent = document.getElementById("note").value;
//	let ID = document.getElementById(id);
	$.ajax({
		url: "/info/"+id.id+"/note",
		type: "PUT",
		contentType: "text/plain",
		data : noteContent,
		success : function (data) {
			console.log(data);
			id.innerHTML = noteContent;
		}
		
	})
	
}	
function Delete(id){
	let noteContent = document.getElementById("note").value;
//	let ID = document.getElementById(id);
	$.ajax({
		url: "/info/"+id.id,
		type: "DELETE",
		contentType: "text/plain",
		success : function () {
			id.innerHTML = "null";
		}
		
	})
}