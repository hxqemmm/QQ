use QQ;
drop table ID;
drop table Users;
drop table News;
drop table Friend;


select * from ID;
select * from Friend

--�����û��˺ű�
 create table ID(						
	--id int identity(1,1) primary key not null,		--���
	--username nvarchar(10),							--��ʵ����
	--ids nvarchar(18),									--���֤����

	qqnumber int primary key not null,				--qq����
	nickname  nvarchar(20) not null,				--�ǳ�
	head nvarchar(20) not null,								--ͷ��
	password nvarchar(16) not null,					--qq����
	birthday nvarchar(30) ,							   --����
	sex nvarchar(1) not null,						--�Ա�
	nation nvarchar(5) not null,					--����
	blood nvarchar(3),								--Ѫ��
	
	
	hobby nvarchar(30),								--����
	IP nvarchar(20) not null,						--ip��ַ
	port int check(port>1024 and port<65535),		--�˿ں�	
	state nvarchar(2) not null,						--״̬
	signature nvarchar(50)							--����ǩ��	


	--age int not null,								--����
	--star nvarchar(3),								--����
	--active	float not null,						--��Ծ����	
)

--������Ϣ��
 create table News(						
	id int identity(1,1) primary key not null,		--���
	news nvarchar(4) not null,						--��Ϣ����
	mid int not null,							--ԭ�˺�
	yid	int not null,							--Ŀ���˺�
	sends datetime not null							--����ʱ��
)
--�������ѱ�
 create table Friend(						
	id int identity(1,1) primary key not null,		--���
	mid int not null,							--�����˺�
	yid int not null,							--�����˺�
	groupname nvarchar(10) not null					--����
)