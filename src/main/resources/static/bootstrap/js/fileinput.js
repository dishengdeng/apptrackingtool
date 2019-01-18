	function uploadfile(id)
		{
		var filechoose = document.getElementById(id+'fileupload');
		var fileselect = document.getElementById(id+'filename');
		filechoose.addEventListener("change",function(){fileselect.value=filechoose.value});
		filechoose.click();
		}

