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

<!-- Product Option -->
<script type="text/template" class="template" id="product-option">
 <option value={{id}}>{{code}}</option>
</script>

<!-- hospital_measure -->
<script type="text/template" class="template" id="single-hospital_measure">
 <td>{{ id }}</td>
 <td>{{ code }}</td>
 <td>{{ name }}</td> 
 <td><span class="view">{{ included }}</span>
     <span class="edit"><input type="checkbox"></span>
 </td>
 <td><span class="view">{{ completed }}</span>
     <span class="edit"><input type="checkbox"></span>
 </td>
 <td><span class="view">{{ confirmed }}</span>
     <span class="edit"><input type="checkbox"></span>
 </td>
 <td><span class="view">{{ accepted }}</span>
     <span class="edit"><input type="checkbox"></span>
 </td>
 <td><span class="view">{{ verified }}</span>
     <span class="edit"><input type="checkbox"></span>
 </td>
 <td class="edit-btn"><button>E</button></td>
 <td class="save-btn"><button>S</button></td>
 <td class="cancel-btn"><button>C</button></td>
</script>
