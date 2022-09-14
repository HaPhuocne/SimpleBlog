
CREATE database simpleBlog
go

use simpleBlog
go


CREATE table tblRole(
	RoleId nvarchar(10) primary key,
	RoleName varchar(50)
)


CREATE table tblAccount(
	Email nvarchar(50) primary key,
	Password nvarchar(100),
	RoleId nvarchar(10) foreign key references tblRole(RoleId),
	Status nvarchar(20)

)

CREATE table tblUser(
	Email nvarchar(50) foreign key references tblAccount(Email),
	Name nvarchar(50),
	PhoneNumber nvarchar(12),
)


CREATE table tblContent(
	ContentId nvarchar(10) primary key,
	ContentName nvarchar(50)
)

CREATE table tblArticle(
		TitleId int identity(0,1) primary key,
		TitleName nvarchar(100),
		Description nvarchar(max),
		ContentID nvarchar(10) Foreign key references tblContent(ContentId),
		Email nvarchar(50) Foreign key references tblAccount(Email),
		PostDate date,
		Status nvarchar(10)
)


CREATE table tblComment(
	TitleId int foreign key references tblArticle(TitleId),
	Email nvarchar(50) foreign key references tblAccount(Email),
	CommentDes nvarchar(max)

)


Create table tblNumberOfPage(
	numberId int primary key,
	numberValue int
)








