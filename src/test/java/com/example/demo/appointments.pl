#!/usr/bin/perl

use strict;
use warnings;e
use DBI;

my $cgi = CGI->new;
my $dbh = DBI->connect('dbi:SQLite:dbname=testdb.db','','',
    {RaiseError => 1, PrintError => 0, HandleError => \&handle_error});

my $searchString = $cgi->param("searchString");
my $sth;

if ($searchString eq "") {
  $sth = $dbh->prepare('SELECT * FROM Appointments');
  $sth->execute;
} else {
 $sth = $dbh->prepare('SELECT * FROM Appointments where Description LIKE ');
 $sth->execute( "%" + $searchString + "%";
}

my $json ={};
     my ($count) = $sth->fetchrow_array;
 if ($count != 0) {
$myjson = qq({);

foreach my $row (@$all) {
my ($appointmentTime, $description) = @$row;
      my ($year, $month, $day, $time) = $appointmentTime =~ /\b(\d{4})(\d{2})(\d{2})\b(\d{4})/;      

myjson += qq{{"dateTime" : "$appointmentTime", "description" : "$description"}}
}
myjson +=qq(});
}

$dbh->disconnect;
  
# return JSON string
print $cgi->header(-type => "application/json", -charset => "utf-8");
print $myjson;

sub handle_error {
  my ($msg, $dbh, $rv) = @_;
  print "DB Error: $msg\n";
  0;
}