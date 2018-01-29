#!/usr/bin/perl

use strict;
use warnings;
use DBI;

#Variables for database credentials
my $driver   = "SQLite";
my $database = "C:\\Users\\Eric\\testDB.db";
my $dsn      = "DBI:$driver:dbname=$database";
my $userid   = "";
my $password = "";

my $dbh = DBI->connect($dsn, $userid,$password,
    {RaiseError => 1, PrintError => 0, HandleError => \&handle_error});

#Retrieving appointment variables from command-line arguments
my $appointmentDate = $ARGV[0];
my $appointmentTime = $ARGV[1];
my $description = $ARGV[2];

my $appointmentDateTime = $appointmentDate . " " . $appointmentTime;
my $sth;

## Only insert appointsments if there is not an empty appointment date/time and description.
if ($appointmentDateTime ne "" && $description ne "") {
	$sth = $dbh->prepare('INSERT INTO APPOINTMENTS(appointmentTime, description) VALUES (?, ?)');
	$sth->execute($appointmentDateTime, $description);
}

#Close the statement and database connection.
$sth->finish();
$dbh->disconnect;

#Print Database error if there is an error with the inserting into the database.
sub handle_error {
  my ($msg, $dbh, $rv) = @_;
  print "DB Error: $msg\n";
  0;
}