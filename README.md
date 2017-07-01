Forum backend, database course project.
<pre>
/api
    /user
        POST
            > {login, password}
            < login
    
    /session
        POST
            > {login, password}
            < login
        GET
            < login
        DELETE
        
    /forum
        GET
            > title
            < {id, title}
        /list
            GET
                < [{id, title}]
            
    /thread
        GET
            > id
            < {id, forum, title, message, user, creationTime, lastUpdate}
        POST
            > {forum, title, message}
            < {id, forum, title, message, user, creationTime, lastUpdate}
        /list
            GET
                > forum, offset, limit
                < [{id, forum, title, message, user, creationTime, lastUpdate}]
    
    /post
        POST
            > {message, threadId, parent}
            < {id, user, message, threadId, parent, creationTime}
        /list
            GET
                > thread, offset, limit, desc?
                < [{id, user, message, threadId, parent, creationTime}]
                
/admin
    /session
        POST
            > {login, password}
            < login
        GET
            < login
        DELETE
        
    /user
        DELETE
            > {login}
            < login
            
    /forum
        POST
            > {title}
            < title
        DELETE
            > {title}
            < title
        /rename
            POST
                > {oldTitle, newTitle}
                < newTitle
            
    /thread
        DELETE
            > {id}
            < id
            
    /post
        DELETE
            > {id}
            < id
