<#import "masterTemplate.ftl" as layout />
<@layout.masterTemplate title="CreateIssue">

<h5 xmlns="http://www.w3.org/1999/html">Create Issue</h5>
<form class="form-horizontal" action="/create" role="form" method="post">

    <div class="form-group">
        <label for="Name" class="col-sm-2 control-label">Name: </label>
        <div class="col-sm-10">
            <input type="text" class="form-control" name="name" placeholder="name" value="${name!}" />
        </div>
    </div>

    <div class="form-group">
        <label for="Author" class="col-sm-2 control-label">Author: </label>
        <div class="col-sm-10">
            <input type="text" class="form-control" name="author" placeholder="author" value="${name!}" />
        </div>
    </div>

    <div class="form-group">
        <label for="description" class="col-sm-2 control-label">Description: </label>
        <div class="col-sm-10">
            <textarea rows="5" type="description" class="form-control" name="description" placeholder="description"></textarea>
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-default">Save</button>
            <button type="submit" class="btn btn-default">Cancel</button>
        </div>
    </div>
</form>
</@layout.masterTemplate>
