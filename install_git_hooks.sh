#! /bin/sh

echo "#!/bin/sh\n
set -x
mvn fmt:check" > .git/hooks/pre-commit

chmod +x .git/hooks/pre-commit

