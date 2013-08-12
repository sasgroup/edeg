<!-- LIST -->
<script type="text/template" class="template" id="hospital-list-template">
<div class="container" >
<div class="row">    
        <h3>List of Hospitals</h3>        
        <div class="clearfix"></div>
        <form method="post" class="form-horizontal" id="hospital-list" accept-charset="utf-8">
			<table id="table_items" class="table table-striped" >
    			<thead>
        			<tr>
            			<th>
                			Name
            			</th>
            			<th>
                			Notes
            			</th>
            			<th class="f-btn">Edit</th>			
        			</tr>
    			</thead>
    			<tbody>    
    			</tbody>
			</table>
        </form>
</div>
</div>
</script>

<!-- NEW/EDIT -->
<script type="text/template" class="template" id="hospital-template">

<div class="container">
   <div class="row">   
   <h3>Hospital: Edit</h3>
   <form method="post" class="form-horizontal" id="hospital-edit" accept-charset="utf-8">    
	<table class="table">
        <tbody>
			  <tr>
                <td>Name</td>
                <td>
                    <input name="name" type="text" value="{{ name }}" id="name" disabled>
                </td>
                <td>Notes</td>
                <td>
                <input name="notes" type="text" value="{{ notes }}" id="notes"> 
                </td>
                <td></td>
             </tr>					

			  <tr>
                <td>Primary EHR</td>
                <td>
                    <select id="slcEHRs" style="width:250px;">                      
                    </select>
                </td>
                <td>Products</td>
                <td>
                    <select id="slcProducts" style="width:370px;">                  
                    </select>
                </td>
                <td><button id="btnApplyHospitalOptions" class="btn btn-info">Apply</button></td>
            </tr>
        </tbody>
    </table>   
   
    <ul id="myTab" class="nav nav-tabs">               
    </ul>     
    <div id="myTabContent" class="tab-content">               
    </div>

	<div class="form-actions">
                    <button id="submit-close-btn" class="btn btn-info pull-right">Save&Close</button>
					<button id="submit-btn" class="btn btn-info pull-right">Save Hospital</button>
					<button id="cancel" class="btn btn-info pull-right">Cancel</button>
					
    </div>
	
	 </form>  
  </div>
</div>
<div id="loading">
  <img id="loading-image" src="http://www.kuka-systems.com/res/icn/ajax_loader.gif" alt="Loading..." />
</div>

</script>

<!-- Product/EHR Option -->
<script type="text/template" class="template" id="product-option">
 <option value={{id}} name={{id}}>{{code}} </option>
</script>


<!-- SINGLE -->
<script type="text/template" class="template" id="single-hospital">
 <td><div class='name' title="{{name}}">{{ name }}</div></td>
 <td><div class='notes' title="{{notes}}">{{ notes }}</div></td>
 <td class="f-btn"><div id="edit" class= "btn"><i class="icon-edit"></i></div></td> 
</script>

<!-- hospital-measure_table -->
<script type="text/template" class="template" id="hospital-measure_table">
<table class="hospitalMeasureTable table table-striped ">
	<thead><tr>
		    <th>Use</th>			
			<th>CODE</th>
		    <th>TITLE</th>				    				  
		    <th>Completed</th>
		    <th>Confirmed</th>
		    <th>Accepted</th>
		    <th>Verified</th>		    
		   </tr>
    </thead>				
    <tbody>		
    </tbody>
</table>
</script>

<!-- hospital_measure -->
<script type="text/template" class="template" id="single-hospital_measure">
 <td>
	<input type="checkbox" name="included" id="{{id}}" {{included}} >
 </td> 
 <td>{{ code }}</td>
 <td><div class='title' title="{{name}}"><a id="customLink" href="#modalDataElements" role="button" data-toggle="modal">{{ name }}</a></div></td>
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