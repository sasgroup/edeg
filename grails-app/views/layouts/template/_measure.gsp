<!-- LIST -->
<script type="text/template" class="template" id="measure-list-template">
<div class="container" >
<div class="row">
    <div class="span8">
        <h3>List of Measures</h3>
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
    </div> <!-- .span8 -->
</div>
</div>
</script>


<!-- NEW -->
<script type="text/template" class="template" id="measure-template">
<div class="container">

    <!-- Modal Product -->
    <div id="modalProducts" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 id="myModalLabel">Products</h3>
        </div>
        <div class="modal-body">
            <div class="control-group">
                <div class="controls checkboxlistModal">
                    <label class="checkbox">
                        <input type="checkbox" name="product" value="option1" id="inlineCheckbox1"> Product1
                    </label>
                    <label class="checkbox">
                        <input type="checkbox" name="product" value="option3" id="inlineCheckbox2"> Product2
                    </label>
                    <label class="checkbox">
                        <input type="checkbox" name="product" value="option4" id="inlineCheckbox3"> Product3
                    </label>
                    <label class="checkbox">
                        <input type="checkbox" name="product" value="option5" id="inlineCheckbox4"> Product4
                    </label>
                    <label class="checkbox">
                        <input type="checkbox" name="product" value="option2" id="inlineCheckbox5"> Product5
                    </label>
                </div>
            </div>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
            <button class="btn btn-info" id="assignProduct"  data-dismiss="modal" aria-hidden="true">Save</button>
        </div>
    </div>

    <!-- Modal Element -->
    <div id="modalElements" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 id="elementModalLabel">Elements</h3>
        </div>
        <div class="modal-body">
            <div class="control-group">
                <div class="controls checkboxlistModalElement">
                    <label class="checkbox">
                        <input type="checkbox" name="product" value="option1" id="inlineCheckbox1"> Element1
                    </label>
                    <label class="checkbox">
                        <input type="checkbox" name="product" value="option3" id="inlineCheckbox2"> Element2
                    </label>
                    <label class="checkbox">
                        <input type="checkbox" name="product" value="option4" id="inlineCheckbox3"> Element3
                    </label>
                    <label class="checkbox">
                        <input type="checkbox" name="product" value="option5" id="inlineCheckbox4"> Element4
                    </label>
                    <label class="checkbox">
                        <input type="checkbox" name="product" value="option2" id="inlineCheckbox5"> Element5
                    </label>
                </div>
            </div>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
            <button class="btn btn-info" id="assignElement"  data-dismiss="modal" aria-hidden="true">Save</button>
        </div>
    </div>


    <div class="row">
        <div class="span8">
            <h3>Measure: New</h3>
            <hr>
            <div class="clearfix"></div>
            <form action="billing" method="post" class="form-horizontal" id="billingform" accept-charset="utf-8">
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

                <div class="control-group">
                    <label for="notes" class="control-label">
                        Products
                    </label>

                    <div class="controls">
                        <a href="#modalProducts" role="button" class="btn" data-toggle="modal">Assign</a>
                    </div>
                </div>


                <!-- Products -->
                <div class="control-group">
                    <div class="controls checkboxlist">
                    </div>
                </div>


                <div class="control-group">
                    <label for="notes" class="control-label">
                        Elements
                    </label>

                    <div class="controls">
                        <a href="#modalElements" role="button" class="btn" data-toggle="modal">Assign</a>
                    </div>
                </div>

                <!-- Elements -->
                <div class="control-group">
                    <div class="controls checkboxlist-elements">
                    </div>
                </div>


                <div class="form-actions">
                    <button type="submit" class="btn btn-info">Save Product</button>

                </div>
            </form>

        </div>
        <!-- .span8 -->

    </div>
</div>
</script>

<!-- SINGLE -->
<script type="text/template" class="template" id="single-measure">
 <td>{{ code }}</td>
 <td>{{ name }}</td>
 <td>{{ notes }}</td> 
 <td><div id="edit" class= "btn"><i class="icon-edit"></i></div></td>
 <td><div id="destroy" class= "btn"><i class="icon-trash"></i></div></td>
</script>
