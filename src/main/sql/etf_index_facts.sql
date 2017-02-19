DROP TABLE IF EXISTS etf_index_net_assets CASCADE;
CREATE TABLE IF NOT EXISTS etf_index_net_assets (
  etf_index_id INT NOT NULL,
  record_date DATE NOT NULL,
  data DECIMAL (32, 6) NOT NULL,
  CONSTRAINT etf_index_net_assets_pkey PRIMARY KEY (etf_index_id, record_date),
  CONSTRAINT etf_index_net_assets_etf_index_id_fkey FOREIGN KEY (etf_index_id) REFERENCES etf_index(id)
);

DROP TABLE IF EXISTS etf_index_shares_outstanding CASCADE;
CREATE TABLE IF NOT EXISTS etf_index_shares_outstanding (
  etf_index_id INT NOT NULL,
  record_date DATE NOT NULL,
  data BIGINT NOT NULL,
  CONSTRAINT etf_index_shares_outstanding_pkey PRIMARY KEY (etf_index_id, record_date),
  CONSTRAINT etf_index_shares_outstanding_etf_index_id_fkey FOREIGN KEY (etf_index_id) REFERENCES etf_index(id)
);

DROP TABLE IF EXISTS etf_index_number_of_holdings CASCADE;
CREATE TABLE IF NOT EXISTS etf_index_number_of_holdings (
  etf_index_id INT NOT NULL,
  record_date DATE NOT NULL,
  data INT NOT NULL,
  CONSTRAINT etf_index_number_of_holdings_pkey PRIMARY KEY (etf_index_id, record_date),
  CONSTRAINT etf_index_number_of_holdings_etf_index_id_fkey FOREIGN KEY (etf_index_id) REFERENCES etf_index(id)
);

DROP TABLE IF EXISTS etf_index_closing_price CASCADE;
CREATE TABLE IF NOT EXISTS etf_index_closing_price (
  etf_index_id INT NOT NULL,
  record_date DATE NOT NULL,
  data DECIMAL(18, 6) NOT NULL,
  CONSTRAINT etf_index_closing_price_pkey PRIMARY KEY (etf_index_id, record_date),
  CONSTRAINT etf_index_closing_price_etf_index_id_fkey FOREIGN KEY (etf_index_id) REFERENCES etf_index(id)
);

DROP TABLE IF EXISTS etf_index_pe_ratio CASCADE;
CREATE TABLE IF NOT EXISTS etf_index_pe_ratio (
  etf_index_id INT NOT NULL,
  record_date DATE NOT NULL,
  data FLOAT NOT NULL,
  CONSTRAINT etf_index_pe_ratio_pkey PRIMARY KEY (etf_index_id, record_date),
  CONSTRAINT etf_index_pe_ratio_etf_index_id_fkey FOREIGN KEY (etf_index_id) REFERENCES etf_index(id)
);

DROP TABLE IF EXISTS etf_index_pb_ratio CASCADE;
CREATE TABLE IF NOT EXISTS etf_index_pb_ratio (
  etf_index_id INT NOT NULL,
  record_date DATE NOT NULL,
  data FLOAT NOT NULL,
  CONSTRAINT etf_index_pb_ratio_pkey PRIMARY KEY (etf_index_id, record_date),
  CONSTRAINT etf_index_pb_ratio_etf_index_id_fkey FOREIGN KEY (etf_index_id) REFERENCES etf_index(id)
);

DROP TABLE IF EXISTS etf_index_distribution_yield CASCADE;
CREATE TABLE IF NOT EXISTS etf_index_distribution_yield (
  etf_index_id INT NOT NULL,
  record_date DATE NOT NULL,
  data FLOAT NOT NULL,
  CONSTRAINT etf_index_distribution_yield_pkey PRIMARY KEY (etf_index_id, record_date),
  CONSTRAINT etf_index_distribution_yield_etf_index_id_fkey FOREIGN KEY (etf_index_id) REFERENCES etf_index(id)
);
