git filter-branch --commit-filter '
        if [ "$GIT_AUTHOR_EMAIL" = "avivfox93@gmail.com" ];
        then
                GIT_AUTHOR_NAME="michal";
                GIT_AUTHOR_EMAIL="michal12758@gmail.com";
                git commit-tree "$@";
        else
                git commit-tree "$@";
        fi' HEAD
