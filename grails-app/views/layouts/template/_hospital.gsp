<!-- LIST -->
<script type="text/template" class="template" id="hospital-list-template">
<div class="container" >
<div class="row">
    <div class="span8">
        <h3>List of Hospitals</h3>
		<section class="row-fluid">
  			<a id="create_hospital">Create New</a>
		</section>
        <hr>
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
   <h3>Hospital: {{state}}</h3> 
    <table class="table">
        <tbody>
            <tr>
                <td>Primary EHR</td>
                <td>
                    <select id="slcEHRs" style="width:150px;">
                        <option value="1">Meditech V5</option>
                        <option value="2">Meditech V6</option>
                        <option value="3">eClinicalWorks</option>
                        <option value="4">Cerner</option>
                    </select>
                </td>
                <td>Products</td>
                <td>
                <select id="slcProducts" style="width:250px;">
                    <option value="MU1">MU1 - Meaningful Use Stage 1</option>
                    <option value="MU2">MU2 - Meaningful Use Stage 2</option>
                    <option value="MU3">MU3 - Meaningful Use Stage 3</option>
                    <option value="IA">IA - Information Alert</option>
                </select>
                </td>
                <td><button id="btnApplyHospitalOptions">Apply</button></td>
            </tr>
        </tbody>
    </table>


        
    <ul id="myTab" class="nav nav-tabs">
               
    </ul>
     
    <div id="myTabContent" class="tab-content">
               
    </div>
       

    

    
</div>
</script>


<!-- SINGLE -->
<script type="text/template" class="template" id="single-hospital">
 <td>{{ name }}</td>
 <td>{{ notes }}</td>
 <td><div id="edit" class= "btn"><i class="icon-edit"></i></div></td>
 <td><div id="destroy" class= "btn"><i class="icon-trash"></i></div></td> 
</script>
