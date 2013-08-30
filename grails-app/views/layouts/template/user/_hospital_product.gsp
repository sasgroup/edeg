<!-- user-hospital-measure_table -->
<script type="text/template" class="template" id="user-hospital-measure_table">
<table class="hospitalMeasureTable table table-striped table-condensed">
	<thead><tr>
		    <th>Use</th>			
			<th>ID</th>
		    <th>Name</th>				    				  
		    <th>Complete</th>		    
		    <th>Accepted</th>
			<th>Needs Review</th>
		    <th>Verified</th>
			<th>Help</th>		    
		   </tr>
    </thead>				
    <tbody>		
    </tbody>
</table>  
</div>
</script>

<!-- user hospital_measure -->
<script type="text/template" class="template" id="user-hospital_measure">
 <td>
	<input type="checkbox" name="included" id="{{id}}" disabled="disabled" {{included}} {{included? "": "title='Contact IHM customer support to change selected measures'"}}>
 </td>
 <td><div class='code' title="{{notes}}">{{ code }}</div></td>
 <td><div class='title' title="{{name}}"><a {{included? "id='customLink' href='#modalDataElements'": "class='disable'"}} role="button" data-toggle="modal">{{ name }}</a></div></td>
 <td>
    <input type="checkbox" name="completed" id="{{id}}" {{completed}} {{included? "": 'disabled="disabled"'}}>
 </td>
 <td>
     <input type="checkbox" name="accepted" id="{{id}}" disabled="disabled" {{accepted}}>
 </td>
 <td>
     <input type="checkbox" name="confirmed" id="{{id}}" disabled="disabled" {{confirmed}}>
 </td>
 <td>
     <input type="checkbox" name="verified" id="{{id}}" disabled="disabled" {{verified}}>
 </td>
 <td>
     <a class="btn show_info" rel="tooltip" mid="{{id}}" title=""><i class="icon-info-sign" mid="{{id}}"></i></a>
 </td>
</script>

<script type="text/template" class="template" id="user-hospital-breadcrumb">
<ul class="breadcrumb">
	<li><a href="#">Home</a><span class="divider">></span></li>	
    <li class="active">{{ product_code }}:</li>
	<li class="active">Measures</li>

    <li class="pull-right"><a class="btn show-help" rel="tooltip" title=""><i class="icon-info-sign"></i></a></li>
	<li class="pull-right"><a class="btn show-notes" rel="tooltip" title=""><i class="icon-comment"></i></a></li>	
</ul>
</script>