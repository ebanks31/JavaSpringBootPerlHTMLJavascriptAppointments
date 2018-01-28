//<![CDATA[
#!/usr/bin/perl

use strict;
use warnings;
use DBI;

my $cgi = CGI->new;
my $dbh = DBI->connect('dbi:SQLite:dbname=testdb.db','','',
    {RaiseError => 1, PrintError => 0, HandleError => \&handle_error});
my $appointmentDate = $cgi->param("appointmentDate");
my $appointmentTime= $cgi->param("appointmentTime");
my $description = $cgi->param("description");
my $appointmentDateTime = $appointmentDate + " " + $appointmentTime;

my $sth = $dbh->prepare('INSERT INTO Appointments VALUES (?, ?)');
$sth->execute($appointmentDateTime, $description);

$dbh->disconnect;

sub handle_error {
  my ($msg, $dbh, $rv) = @_;
  print "DB Error: $msg\n";
  0;
}
//]]>