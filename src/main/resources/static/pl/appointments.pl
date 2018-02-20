#!/usr/bin/perl

use strict;
use warnings;
use DBI;
use CGI;

#Variables for database credentials
my $driver   = "SQLite";
my $database = "C:\\Users\\Eric\\testDB.db";
my $dsn      = "DBI:$driver:dbname=$database";
my $userid   = "";
my $password = "";

my $cgi = CGI->new;
my $dbh = DBI->connect($dsn, $userid,$password,
    {RaiseError => 1, PrintError => 0, HandleError => \&handle_error});

my $sth;

#Retrieve search string from command-line arguments.
my $searchString = $ARGV[0];

#if searchString is found in arguments then search any appointments that contains the search string.
#Otherwise  get all appointments.
if ($searchString eq "") {
  $sth = $dbh->prepare('SELECT DISTINCT appointmentTime, Description FROM APPOINTMENTS');
  $sth->execute;
} else {
  $sth = $dbh->prepare('SELECT DISTINCT appointmentTime, Description FROM APPOINTMENTS where Description LIKE ?');
  $sth->execute("%" . $searchString . "%");
}

#Initialize JSON to empty
my $appointmentJSON ={};

#Retrieve all rows from query.
my ($all) = $sth->fetchall_arrayref;

#Only populate JSON when results are found.
if ($all != 0) {
	$appointmentJSON = qq([);

	#Iterate through each row and create JSON format to send to JavaScript front-end.
	foreach my $row (@$all) {
		my ($appointmentDateTime, $description) = @$row;
		my ($appointmentDate, $appointmentTime) = split / /, $appointmentDateTime;
		$appointmentJSON .= qq{{"date":"$appointmentDate","time":"$appointmentTime","description":"$description"},};
	}

	#Remove the last comma from JSON.
	$appointmentJSON =~ s/,$//;
	$appointmentJSON .=qq(]);
}

#Close the statement and database connection.
$sth->finish();
$dbh->disconnect;

#Return appointment JSON format.
print $cgi->header(-type => "application/json", -charset => "utf-8");
print $appointmentJSON;

#Print Database error if there is an error with the retrieving from the database.
sub handle_error {
  my ($msg, $dbh, $rv) = @_;
  print "DB Error: $msg\n";
  0;
}