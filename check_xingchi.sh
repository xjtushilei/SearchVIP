PATH=/usr/local/bin:/bin:/usr/bin:/usr/local/sbin:/usr/sbin:/sbin
. /etc/profile
export LANG=en_US.utf-8
exist=`ps aux | grep -w 'java -jar xingchi.jar'| grep -v 'grep' |  awk '{print $2}'`
if [ -z "$exist" ] ;
then
    echo 1
    nowtime=`date +"%Y-%m-%d %H:%M:%S"`
    echo $nowtime"xingchi.jar  can not find"
    cd /home/ubuntu/project/SearchVIP/build/libs/
    nohup java -jar xingchi.jar -Xmx256m &
fi


# 在crontab中添加  */3 * * * * sh /home/ubuntu/crontab/check_xingchi.sh >> /home/ubuntu/crontab/logs/check_xingchi.log &
