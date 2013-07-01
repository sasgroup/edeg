<!-- LIST -->
<script type="text/template" class="template" id="element-list-template">
<div class="container" >
    <div class="row">
        <div class="span8">
            <h3>List of DataElements</h3>
    		<section class="row-fluid">
      			<a id="create_dataElement">Create New</a>
    		</section>
            <hr>    		
            <div class="clearfix"></div>
            <form method="post" class="form-horizontal" id="element-list" accept-charset="utf-8">
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


<!-- NEW -->
<script type="text/template" class="template" id="element-new-template">
<div class="container">  
    <div class="row">
        <div class="span8">
            <h3>DataElement: New</h3>
            <hr>
            <div class="clearfix"></div>
            <form method="post" class="form-horizontal" id="element-new" accept-charset="utf-8">
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
                        <textarea rows="3"></textarea>
                    </div>
                </div>
                
                <ul id="myTab" class="nav nav-tabs">
                  <li class="active">
                    <a data-toggle="tab" href="#measures">Measure</a>
                  </li>
                  <li class="">
                    <a data-toggle="tab" href="#ehrs">EHR</a>
                  </li>
                </ul>
     
                <div id="myTabContent" class="tab-content">
                   <div id="measures" class="tab-pane fade active in">    
                   </div>
                   <div id="ehrs" class="tab-pane fade">              
                   </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-info">Save DataElement</button>
                </div>
            </form>
        </div>        
    </div>
</div>
</script>


<!-- EDIT -->
<script type="text/template" class="template" id="element-edit-template">
<div class="container">
    <div class="row">
        <div class="span8">
            <h3>DataElement: Edit</h3>
            <hr>
            <div class="clearfix"></div>
            <form method="post" class="form-horizontal" id="element-edit" accept-charset="utf-8">
                <div class="control-group">
                    <label for="code" class="control-label">
                        Code
                    </label>
                    <div class="controls">
                        <input name="code" type="text" value="{{ code }}" id="code">
                    </div>
                </div>
                <div class="control-group">
                    <label for="name" class="control-label">
                        Name
                    </label>
                    <div class="controls">
                        <input name="name" type="text" value="{{ name }}" id="name">
                    </div>
                </div>
                <div class="control-group">
                    <label for="notes" class="control-label">
                        Notes
                    </label>
                    <div class="controls">
                        <textarea rows="3" name="notes" id="notes">{{ notes }}</textarea>
                    </div>
                </div>

                <ul id="myTab" class="nav nav-tabs">
                  <li class="active">
                    <a data-toggle="tab" href="#measures">Measure</a>
                  </li>
                  <li class="">
                    <a data-toggle="tab" href="#ehrs">EHR</a>
                  </li>
                </ul>
     
                <div id="myTabContent" class="tab-content">
                   <div id="measures" class="tab-pane fade active in">    
                   </div>
                   <div id="ehrs" class="tab-pane fade">              
                   </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-info">Save DataElement</button>
                </div>
            </form>
        </div>
    </div>
</div>
</script>


<!-- SINGLE -->
<script type="text/template" class="template" id="single-element">
 <td>{{ code }}</td>
 <td>{{ name }}</td>
 <td>{{ notes }}</td>
 <td><div id="edit" class= "btn"><i class="icon-edit"></i></div></td>
 <td><div id="destroy" class= "btn"><i class="icon-trash"></i></div></td> 
</script>

<script type="text/template" class="template" id="single-element-measure">
    <label class="checkbox">
        <input type="checkbox" name="measure" value="{{name}}" id="{{id}}" {{ch}}> {{name}}
    </label>
</script>


<script type="text/template" class="template" id="single-element-ehr">
    <label class="checkbox">
        <input type="checkbox" name="ehr" value="{{name}}" id="{{id}}" {{ch}}> {{name}}
    </label>
</script>