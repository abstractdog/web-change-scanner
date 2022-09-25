LocalStorageBackup = {
	getName: function(element){
		var prefix = "local-storage-backup-";
		
		if (element.attr("data-stored-name")){
			return prefix + element.attr("data-stored-name");
		}
		
		if (element.attr("id")){
			return prefix + element.attr("id");
		}
		if (element.attr("name")) {
			return prefix + element.attr("name");
		}
		
		if (element.attr("name")) {
			return prefix + element.prop("tagName") + "_" + element.attr("class");
		}
	},
	
	isValidValue: function(value){
		return value && String(value) != "null"
	},
	
	storeValue: function(element, name){
		console.log(`store: ${name} : ${element.val()}`);

		localStorage.setItem(name, element.val())
	},
	
	setValue: function(element, value){
		if (element.prop("tagName").toLowerCase() == "select"){
			value = value.split(",")
			
			var values = $.map($('option', element) ,function(option) {
				return option.value;
			});

			for (v of value){
				if (values.indexOf(v) == -1){
					element.append('<option value="' + v + '">' + v + '</option>');
				}
			}
		}
		
		element.val(value);
		
		if (element.prop("tagName").toLowerCase() == "select"){
			element.trigger('change');
		}
	}
}
window.onload = function() {
	$(function(){
		$(".local-storage-backup").each(function(){
			var element = $(this);
			var name = LocalStorageBackup.getName(element);
			var value = localStorage.getItem(name);
			
			if (LocalStorageBackup.isValidValue(value)){
				console.log(`load: ${name} : ${value}`);
				LocalStorageBackup.setValue(element, value);
			}
		})
		
		$(".local-storage-backup").on("change", function(){
			var element = $(this);
			var name = LocalStorageBackup.getName(element);

			LocalStorageBackup.storeValue(element, name);
		})
	})
}