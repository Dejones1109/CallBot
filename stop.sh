pids=`ps axf | grep Api-0.1.jar | grep -v grep | awk '{print $1}'`
echo "Kill process: " $pids
kill -9 $pids
rm -f Api-0.1.pid
