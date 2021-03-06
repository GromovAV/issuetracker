<#macro masterTemplate title="Welcome">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>${title} | IssueTracker</title>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div class="container">
    <nav class="navbar navbar-default backColor" role="navigation">
        <div class="navbar-header">
            <a class="navbar-brand" href="/">IssueTracker</a>
        </div>

        <div class="collapse navbar-collapse" id="example-navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <#if user??>
                    <li><a href="/create">Create Issue</a></li>
                    <li><a href="/logout">Sign Out [${user.login}]</a></li>
                <#else>
                    <li><a href="/register">Sign Up</a></li>
                    <li><a href="/login">Sign In</a></li>
                </#if>
            </ul>
        </div>
    </nav>

    <div class="container">
        <#nested />
    </div>
</div>
</body>
</html>
</#macro>