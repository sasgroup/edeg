<!-- LIST -->
<script type="text/template" class="template" id="hospital-list-template">
<div class="container" >
<div class="row">
    <div class="span8">
        <h3>List of Hospitals</h3>        
        <div class="clearfix"></div>
        <form method="post" class="form-horizontal" accept-charset="utf-8">

<table id="table_items" class="table table-striped" >
    <thead>
        <tr>
            <th>
                Name
            </th>
            <th>
                Notes
            </th>
           <th>Edit</th>
			<th>Delete</th>
        </tr>
    </thead>

    <tbody>    
    </tbody>
</table>

        </form>
    </div> 
</div>
</div>
</script>

<!-- NEW/EDIT -->
<script type="text/template" class="template" id="hospital-template">
<div class="container">
   <div class="row">   
   <h3>Hospital: Edit</h3>
   <form method="post" class="form-horizontal" id="ehr-edit" accept-charset="utf-8">    
    			<div class="control-group">
                    <label for="code" class="control-label">
                        Code
                    </label>
                    <div class="controls">
                        <input name="code" type="text" value="" id="code">
                    </div>
                </div>
                <div class="control-group">
                    <label for="name" class="control-label">
                        Name
                    </label>
                    <div class="controls">
                        <input name="name" type="text" value="" id="name">
                    </div>
                </div>
                <div class="control-group">
                    <label for="notes" class="control-label">
                        Notes
                    </label>
                    <div class="controls">
                        <textarea rows="3" name="notes" id="notes"></textarea>
                    </div>
                </div>

		

	<table class="table">
        <tbody>
			  <tr>
                <td>Primary EHR</td>
                <td>
                    <select id="slcEHRs" style="width:200px;">                      
                    </select>
                </td>
                <td>Products</td>
                <td>
                <select id="slcProducts" style="width:350px;">                  
                </select>
                </td>
                <td><button id="btnApplyHospitalOptions">Apply</button></td>
            </tr>
        </tbody>
    </table>   
    <hr>
       
    <ul id="myTab" class="nav nav-tabs">               
    </ul>     
    <div id="myTabContent" class="tab-content">               
    </div>
	 </form>  
  </div>
</div>
</script>

<!-- Product/EHR Option -->
<script type="text/template" class="template" id="product-option">
 <option value={{id}} name={{id}}>{{code}} </option>
</script>


<!-- SINGLE -->
<script type="text/template" class="template" id="single-hospital">
 <td>{{ name }}</td>
 <td>{{ notes }}</td>
 <td><div id="edit" class= "btn"><i class="icon-edit"></i></div></td>
 <td><div id="destroy" class= "btn"><i class="icon-trash"></i></div></td> 
</script>

<!-- hospital-measure_table -->
<script type="text/template" class="template" id="hospital-measure_table">
<table class="hospitalMeasureTable table table-striped ">
	<thead><tr>
		    <th>ID</th>           
			<th>CODE</th>
		    <th>TITLE</th>
		    <th>Use</th>				  
		    <th>Completed</th>
		    <th>Confirmed</th>
		    <th>Accepted</th>
		    <th>Cerified</th>		    
		   </tr>
    </thead>				
    <tbody></tbody>
</table>
</script>

<!-- hospital_measure -->
<script type="text/template" class="template" id="single-hospital_measure">
 <td>{{ id }}</td>
 <td>{{ code }}</td>
 <td>{{ name }}</td> 
 <td>
	<input type="checkbox" name="included" id="{{id}}" {{included}} >
 </td>
 <td>
    <input type="checkbox" name="completed" id="{{id}}" {{completed}}>
 </td>
 <td>
     <input type="checkbox" name="confirmed" id="{{id}}" {{confirmed}}>
 </td>
 <td>
     <input type="checkbox" name="accepted" id="{{id}}" {{accepted}}>
 </td>
 <td>
     <input type="checkbox" name="verified" id="{{id}}" {{verified}}>
 </td> 
</script>
