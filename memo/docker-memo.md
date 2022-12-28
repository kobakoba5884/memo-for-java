<h1 align="center">memo-for-docker-command</h1>

# command 
***
- when you just wanna remove image (tag version is none etc..)

```
    docker rmi $(docker images -f 'dangling=true' -q)
```
        rmi = image rm
        
        -f, --filter filter   Filter output based on conditions provided
            --format string   Pretty-print images using a Go template
            --no-trunc        Don't truncate output
        -q, --quiet           Only show image IDs