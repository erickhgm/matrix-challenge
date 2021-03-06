drop table if exists dna_sequence cascade;

create table dna_sequence (
	id int8 generated by default as identity,
	sequence VARCHAR not null,
	is_simian boolean not null,
	primary key (id)
);

create index idx_dna_sequence on dna_sequence (sequence);

alter table if exists dna_sequence add constraint uk_idx_dna_sequence unique (sequence);

insert into dna_sequence (sequence, is_simian) values ('AAAA-ACGT-ACGT-ACGT', true);
insert into dna_sequence (sequence, is_simian) values ('ACGT-CCCC-ACGT-ACGT', true);
insert into dna_sequence (sequence, is_simian) values ('ACGT-ACGT-GGGG-ACGT', true);
insert into dna_sequence (sequence, is_simian) values ('ACGT-ACGT-ACGT-TTTT', true);
insert into dna_sequence (sequence, is_simian) values ('AAAA-CCCC-GGGG-TTTT', true);
insert into dna_sequence (sequence, is_simian) values ('ACTT-CTGT-ACGT-ACGT', false);
insert into dna_sequence (sequence, is_simian) values ('CGCT-TGCA-TGCA-TGAA', false);
insert into dna_sequence (sequence, is_simian) values ('GTCT-ATGT-ATAG-ATAT', false);
insert into dna_sequence (sequence, is_simian) values ('TGAT-AGCG-AGTG-AGTG', false);
insert into dna_sequence (sequence, is_simian) values ('ACAA-ACAG-TCAA-ACGA', false);