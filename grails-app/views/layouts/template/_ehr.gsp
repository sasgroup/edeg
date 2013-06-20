<!-- LIST -->
<script type="text/template" class="template" id="ehr-list-template">
<div class="container" >
<div class="row">
    <div class="span8">
        <h3>List of EHR</h3>
		<section class="row-fluid">
  			<a id="create_measure">Create New</a>
		</section>
        <hr>
        <div class="clearfix"></div>
        <form action="billing" method="post" class="form-horizontal" id="billingform" accept-charset="utf-8">

<table id="table_items" class="table table-striped" >
    <thead>
        <tr>
            <th>
                Code
            </th>
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


<!-- SINGLE -->
<script type="text/template" class="template" id="single-ehr">
 <td>{{ code }}</td>
 <td>{{ name }}</td>
 <td>{{ notes }}</td> 
 <td><div id="edit" class= "btn"><i class="icon-edit"></i></div></td>
 <td><div id="destroy" class= "btn"><i class="icon-trash"></i></div></td>
</script>
