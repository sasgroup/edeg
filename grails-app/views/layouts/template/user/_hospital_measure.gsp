<!-- user-hospital-measure_table -->
<script type="text/template" class="template" id="user-hospital-measure_table">
<table class="hospitalMeasureTable table table-striped ">
	<thead><tr>
		   <!-- <th>Use</th>-->			
			<th>CODE</th>
		    <th>TITLE</th>				    				  
		    <th>Completed</th>		    
		    <th>Accepted</th>
			<th>Declined</th>
		    <th>Verified</th>		    
		   </tr>
    </thead>				
    <tbody>		
    </tbody>
</table>
</script>

<!-- user hospital_measure -->
<script type="text/template" class="template" id="user-single-hospital_measure">
<!-- <td>
	<input type="checkbox" name="included" id="{{id}}" {{included}} >
 </td> -->
 <td>{{ code }}</td>
 <td><div class='title' title="{{name}}"><a id="customLink" href="#modalDataElements" role="button" data-toggle="modal">{{ name }}</a></div></td>
 <td>
    <input type="checkbox" name="completed" id="{{id}}" {{completed}}>
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
</script>

<script type="text/template" class="template" id="user-hospital-breadcrumb">
<ul class="breadcrumb">
	<li><a href="#">Home</a><span class="divider">></span></li>
	<li><a href="#">{{ product_code }}</a><span class="divider">></span></li>
	<li class="active">Measures</li>
</ul>
</script>