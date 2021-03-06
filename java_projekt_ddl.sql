create database JavaProjekt;
USE [JavaProjekt]
GO
/****** Object:  Table [dbo].[MovieActor]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MovieActor](
	[MovieID] [int] NULL,
	[ActorID] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[MovieRedatelj]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MovieRedatelj](
	[MovieID] [int] NULL,
	[RedateljID] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Movies]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Movies](
	[IDMovie] [int] IDENTITY(1,1) NOT NULL,
	[Title] [nvarchar](255) NULL,
	[pubDate] [nvarchar](255) NULL,
	[Description] [nvarchar](2000) NULL,
	[OrginNaziv] [nvarchar](255) NULL,
	[Trajanje] [int] NULL,
	[Plakat] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[IDMovie] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[MovieZanr]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MovieZanr](
	[MovieID] [int] NULL,
	[ZanrID] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Persons]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Persons](
	[IDPerson] [int] IDENTITY(1,1) NOT NULL,
	[FirstName] [nvarchar](255) NULL,
	[LastName] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[IDPerson] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[username] [nvarchar](25) NOT NULL,
	[password] [nvarchar](255) NOT NULL,
	[isAdmin] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Zanrs]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Zanrs](
	[IDZanr] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[IDZanr] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[MovieActor]  WITH CHECK ADD FOREIGN KEY([ActorID])
REFERENCES [dbo].[Persons] ([IDPerson])
GO
ALTER TABLE [dbo].[MovieActor]  WITH CHECK ADD FOREIGN KEY([MovieID])
REFERENCES [dbo].[Movies] ([IDMovie])
GO
ALTER TABLE [dbo].[MovieRedatelj]  WITH CHECK ADD FOREIGN KEY([MovieID])
REFERENCES [dbo].[Movies] ([IDMovie])
GO
ALTER TABLE [dbo].[MovieRedatelj]  WITH CHECK ADD FOREIGN KEY([RedateljID])
REFERENCES [dbo].[Persons] ([IDPerson])
GO
ALTER TABLE [dbo].[MovieZanr]  WITH CHECK ADD FOREIGN KEY([MovieID])
REFERENCES [dbo].[Movies] ([IDMovie])
GO
ALTER TABLE [dbo].[MovieZanr]  WITH CHECK ADD FOREIGN KEY([ZanrID])
REFERENCES [dbo].[Zanrs] ([IDZanr])
GO
/****** Object:  StoredProcedure [dbo].[createMovie]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[createMovie]
	@Title nvarchar(255),
	@PublishedDate nvarchar(255),
	@Description nvarchar(2000),
	@OrginNaziv nvarchar(255),
	@Trajanje int,
	@Plakat nvarchar(255),
	@Id INT OUTPUT
AS 
BEGIN 


	INSERT INTO Movies VALUES(@Title, @PublishedDate, @Description, @OrginNaziv,@Trajanje,@Plakat)
	SET @Id = SCOPE_IDENTITY()
end


GO
/****** Object:  StoredProcedure [dbo].[createMovieActor]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[createMovieActor]
	@MovieID int,
	@PersonID int
AS 
BEGIN 

IF NOT EXISTS(Select * from MovieActor where MovieID=@MovieID and ActorID= @PersonID)
begin
	INSERT INTO MovieActor VALUES(@MovieID, @PersonID)
	end
END
GO
/****** Object:  StoredProcedure [dbo].[createMovieRedatelj]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[createMovieRedatelj]
	@MovieID int,
	@RedateljID int
AS 
BEGIN 

IF NOT EXISTS(Select * from MovieRedatelj where MovieID=@MovieID and RedateljID= @RedateljID)
begin
	INSERT INTO MovieRedatelj VALUES(@MovieID, @RedateljID)
END
end
GO
/****** Object:  StoredProcedure [dbo].[createMovieZanr]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[createMovieZanr]
	@MovieID int,
	@ZanrID int
AS 
BEGIN 
if not exists(select * from MovieZanr where MovieID=@MovieID and ZanrID=@ZanrID)
begin
	INSERT INTO MovieZanr VALUES(@MovieID, @ZanrID)
END
end
GO
/****** Object:  StoredProcedure [dbo].[createPersonIfNotExists]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
  CREATE PROCEDURE [dbo].[createPersonIfNotExists]
	@FirstName nvarchar(255),
	@LastName nvarchar(255),
	@Id INT OUTPUT
AS 
BEGIN 
 
 if not exists(select * from Persons where FirstName=@FirstName and LastName=@LastName)
 begin

	INSERT INTO Persons VALUES(@FirstName, @LastName)
	SET @Id = SCOPE_IDENTITY()

	end
	else
	begin
	SET @Id = (select IDPerson from Persons where FirstName=@FirstName and LastName=@LastName)
	end 
END
GO
/****** Object:  StoredProcedure [dbo].[createUser]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[createUser]
	@username nvarchar(25),
	@password nvarchar(255),
	@isadmin bit
AS 
BEGIN 


INSERT INTO [dbo].[Users]
           ([username]
           ,[password]
           ,[isAdmin])
     VALUES
           (@username 
           ,@password
           ,@isadmin)



END
GO
/****** Object:  StoredProcedure [dbo].[createZanrIfNotExists]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
Create PROCEDURE [dbo].[createZanrIfNotExists]
	@Name nvarchar(255),
	@Id INT OUTPUT
AS 
BEGIN 
if not exists(select * from Zanrs where Name=@Name)
 begin
	INSERT INTO Zanrs VALUES(@Name)
	SET @Id = SCOPE_IDENTITY()
		end

	else
	begin
	SET @Id = (select IDZanr from Zanrs where Name=@Name)
	end
END
GO
/****** Object:  StoredProcedure [dbo].[deleteAllGenres]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROCEDURE [dbo].[deleteAllGenres]
	AS 
BEGIN 

delete from MovieZanr
delete from Zanrs

END
GO
/****** Object:  StoredProcedure [dbo].[deleteAllMovies]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROCEDURE [dbo].[deleteAllMovies]
	AS 
BEGIN 
delete from MovieRedatelj
delete from MovieActor
delete from MovieZanr
delete from Movies

END
GO
/****** Object:  StoredProcedure [dbo].[deleteAllPersons]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROCEDURE [dbo].[deleteAllPersons]
	AS 
BEGIN 

delete from MovieActor
delete from MovieRedatelj
delete from Persons

END
GO
/****** Object:  StoredProcedure [dbo].[deleteGenre]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROCEDURE [dbo].[deleteGenre]
	@Id INT	 
AS 
BEGIN 
delete from MovieZanr  where ZanrID = @Id

delete from Zanrs where IDZanr = @Id

END
GO
/****** Object:  StoredProcedure [dbo].[deleteMovie]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[deleteMovie]
	@IdMovie INT	 
AS 
BEGIN 
delete from MovieActor where MovieID = @IdMovie
delete from MovieZanr where MovieID = @IdMovie
delete from MovieRedatelj where MovieID = @IdMovie
DELETE
	FROM 
			Movies
	WHERE 
		IDmovie = @IdMovie
END
GO
/****** Object:  StoredProcedure [dbo].[deleteMovieActor]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROCEDURE [dbo].[deleteMovieActor]
	@MovieID int,
	@PersonID int
AS 
BEGIN 
	delete from MovieActor where MovieID=@MovieID and ActorID=@PersonID
END
GO
/****** Object:  StoredProcedure [dbo].[deleteMovieDirector]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROCEDURE [dbo].[deleteMovieDirector]
	@MovieID int,
	@PersonID int
AS 
BEGIN 
	delete from MovieRedatelj where MovieID=@MovieID and RedateljID=@PersonID
END
GO
/****** Object:  StoredProcedure [dbo].[deleteMovieGenre]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROCEDURE [dbo].[deleteMovieGenre]
	@MovieID int,
	@GenreID int
AS 
BEGIN 
	delete from MovieZanr where MovieID=@MovieID and ZanrID=@GenreID
END
GO
/****** Object:  StoredProcedure [dbo].[deletePerson]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[deletePerson]
	@Id INT	 
AS 
BEGIN 
delete from MovieActor  where ActorID = @Id
delete from MovieRedatelj  where RedateljID = @Id
delete from Persons where IDPerson = @Id

END
GO
/****** Object:  StoredProcedure [dbo].[selectActorsFromMovie]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[selectActorsFromMovie]
	@IdMovie INT
AS 
BEGIN 
	select IDPerson,FirstName,LastName from Persons
	inner join MovieActor on MovieActor.ActorID=Persons.IDPerson
where MovieActor.MovieID=@IdMovie
END
GO
/****** Object:  StoredProcedure [dbo].[selectGenre]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROCEDURE [dbo].[selectGenre]
	@Id INT
AS 
BEGIN 
	SELECT 
		* 
	FROM 
		Zanrs
	WHERE 
		IDZanr = @Id
END
GO
/****** Object:  StoredProcedure [dbo].[selectMovie]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[selectMovie]
	@IdMovie INT
AS 
BEGIN 
	SELECT 
		* 
	FROM 
		Movies
	WHERE 
		IDmovie = @IdMovie
END
GO
/****** Object:  StoredProcedure [dbo].[selectMovies]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[selectMovies]
AS 
BEGIN 
	SELECT * FROM Movies
END
GO
/****** Object:  StoredProcedure [dbo].[selectPeople]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROCEDURE [dbo].[selectPeople]

AS 
BEGIN 
	SELECT 
		* 
	FROM 
		Persons
	
END
GO
/****** Object:  StoredProcedure [dbo].[selectPerson]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[selectPerson]
	@IdPerson INT
AS 
BEGIN 
	SELECT 
		* 
	FROM 
		Persons
	WHERE 
		IDPerson = @IdPerson
END
GO
/****** Object:  StoredProcedure [dbo].[selectRedateljsFromMovie]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROCEDURE [dbo].[selectRedateljsFromMovie]
	@IdMovie INT
AS 
BEGIN 
	select * from Persons where IDPerson in (Select RedateljID from MovieRedatelj where MovieID=@IdMovie)
END
GO
/****** Object:  StoredProcedure [dbo].[selectUser]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[selectUser]
	@username nvarchar(25),
	@password nvarchar(255)
AS 
BEGIN 
	SELECT 
		isadmin 
	FROM 
		Users
	WHERE 
		Username = @username and Password=@password
END
GO
/****** Object:  StoredProcedure [dbo].[selectUsername]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROCEDURE [dbo].[selectUsername]
	@username nvarchar(25)
AS 
BEGIN 
	SELECT 
		isadmin 
	FROM 
		Users
	WHERE 
		Username = @username 
END
GO
/****** Object:  StoredProcedure [dbo].[selectZanrs]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROCEDURE [dbo].[selectZanrs]

AS 
BEGIN 
	SELECT 
		* 
	FROM 
		Zanrs
	
END
GO
/****** Object:  StoredProcedure [dbo].[selectZanrsFromMovie]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[selectZanrsFromMovie]
	@IdMovie INT
AS 
BEGIN 
	select * from Zanrs where IDZanr in (Select ZanrID from MovieZanr where MovieID=@IdMovie)
END
GO
/****** Object:  StoredProcedure [dbo].[updateGenre]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROCEDURE [dbo].[updateGenre]
	@Name nvarchar(255),
	
	
		@Id INT
AS 
BEGIN 
	UPDATE Zanrs SET 
		Name = @Name
		
		
	WHERE 
		IDZanr	 = @Id
END
GO
/****** Object:  StoredProcedure [dbo].[updateMovie]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[updateMovie]
	@Title nvarchar(255),
	@PublishedDate nvarchar(255),
	@Description nvarchar(2000),
	@OrginNaziv nvarchar(255),
	@Trajanje int,
	@Plakat nvarchar(255),
	@IdMovie INT
AS 
BEGIN 
delete from MovieActor where MovieID=@IdMovie
delete from MovieZanr where MovieID=@IdMovie
delete from MovieRedatelj where MovieID=@IdMovie

	UPDATE Movies SET 
		Title = @Title,
		pubDate = @PublishedDate,
		Description = @Description,
		OrginNaziv = @OrginNaziv,
		Trajanje = @Trajanje,
		Plakat=@Plakat
	WHERE 
		IDMovie = @IdMovie
END
GO
/****** Object:  StoredProcedure [dbo].[updatePerson]    Script Date: 16.8.2021. 13:35:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROCEDURE [dbo].[updatePerson]
	@FirstName nvarchar(255),
	@LastName nvarchar(255),
	
		@IdPerson INT
AS 
BEGIN 
	UPDATE Persons SET 
		FirstName = @FirstName,
		LastName = @LastName
		
	WHERE 
		IDPerson	 = @IdPerson
END
GO


INSERT INTO [dbo].[Users]
           ([username]
           ,[password]
           ,[isAdmin])
     VALUES
           ('admin'
           ,'admin'
           ,1)
GO


