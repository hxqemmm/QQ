use QQ;
drop table ID;
drop table Users;
drop table News;
drop table Friend;


select * from ID;
select * from Friend

--创建用户账号表
 create table ID(						
	--id int identity(1,1) primary key not null,		--编号
	--username nvarchar(10),							--真实姓名
	--ids nvarchar(18),									--身份证号码

	qqnumber int primary key not null,				--qq号码
	nickname  nvarchar(20) not null,				--昵称
	head nvarchar(20) not null,								--头像
	password nvarchar(16) not null,					--qq密码
	birthday nvarchar(30) ,							   --生日
	sex nvarchar(1) not null,						--性别
	nation nvarchar(5) not null,					--民族
	blood nvarchar(3),								--血型
	
	
	hobby nvarchar(30),								--爱好
	IP nvarchar(20) not null,						--ip地址
	port int check(port>1024 and port<65535),		--端口号	
	state nvarchar(2) not null,						--状态
	signature nvarchar(50)							--个性签名	


	--age int not null,								--年龄
	--star nvarchar(3),								--星座
	--active	float not null,						--活跃天数	
)

--创建消息表
 create table News(						
	id int identity(1,1) primary key not null,		--编号
	news nvarchar(4) not null,						--消息类型
	mid int not null,							--原账号
	yid	int not null,							--目标账号
	sends datetime not null							--发送时间
)
--创建好友表
 create table Friend(						
	id int identity(1,1) primary key not null,		--编号
	mid int not null,							--所属账号
	yid int not null,							--朋友账号
	groupname nvarchar(10) not null					--组名
)