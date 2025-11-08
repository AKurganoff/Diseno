--
-- PostgreSQL database dump
--

\restrict jQW3SgCo5uvCr5sl2fscLNox4zNSVbOEuku2768ebybpE8XdqR9HJ0cuUmEGlW2

-- Dumped from database version 18.0
-- Dumped by pg_dump version 18.0

-- Started on 2025-11-07 18:35:13

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA public;


--
-- TOC entry 5230 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 236 (class 1259 OID 16710)
-- Name: cheque; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.cheque (
    id_mediopago integer,
    monto double precision NOT NULL,
    dni_titular integer NOT NULL,
    CONSTRAINT cheque_monto_check CHECK ((monto > (0)::double precision))
);


--
-- TOC entry 228 (class 1259 OID 16552)
-- Name: consumible; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.consumible (
    id_condumible integer NOT NULL,
    nombre character varying(50) NOT NULL,
    precio integer NOT NULL,
    id_estadia integer,
    CONSTRAINT consumible_precio_check CHECK ((precio > 0))
);


--
-- TOC entry 234 (class 1259 OID 16683)
-- Name: credito; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.credito (
    id_mediopago integer,
    nro_tarjeta integer NOT NULL,
    cuotas integer NOT NULL,
    nombre_titular character varying(15) NOT NULL,
    apellido_titular character varying(15) NOT NULL
);


--
-- TOC entry 235 (class 1259 OID 16697)
-- Name: debito; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.debito (
    id_mediopago integer,
    nro_tarjeta integer NOT NULL,
    nombre_titular character varying(15) NOT NULL,
    apellido_titular character varying(15) NOT NULL
);


--
-- TOC entry 219 (class 1259 OID 16389)
-- Name: direccion; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.direccion (
    calle character varying(50) NOT NULL,
    numero integer NOT NULL,
    departamento character varying(5) NOT NULL,
    piso integer NOT NULL,
    cod_postal integer NOT NULL,
    localidad character varying(50) NOT NULL,
    provincia character varying(50) NOT NULL,
    pais character varying(50) NOT NULL,
    CONSTRAINT direccion_cod_postal_check CHECK ((cod_postal > 0)),
    CONSTRAINT direccion_numero_check CHECK ((numero > 0)),
    CONSTRAINT direccion_piso_check CHECK ((piso > 0))
);


--
-- TOC entry 241 (class 1259 OID 16776)
-- Name: dobleestandar; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.dobleestandar (
    id_tipo integer NOT NULL,
    cant_simple integer NOT NULL,
    cant_dobles integer NOT NULL,
    CONSTRAINT dobleestandar_cant_dobles_check CHECK (((cant_dobles < 2) AND (cant_dobles >= 0))),
    CONSTRAINT dobleestandar_cant_simple_check CHECK (((cant_simple < 3) AND (cant_simple >= 0)))
);


--
-- TOC entry 242 (class 1259 OID 16791)
-- Name: doblesuperior; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.doblesuperior (
    id_tipo integer NOT NULL,
    cant_simple integer NOT NULL,
    cant_king integer NOT NULL,
    CONSTRAINT doblesuperior_cant_king_check CHECK (((cant_king < 2) AND (cant_king >= 0))),
    CONSTRAINT doblesuperior_cant_simple_check CHECK (((cant_simple < 3) AND (cant_simple >= 0)))
);


--
-- TOC entry 237 (class 1259 OID 16723)
-- Name: efectivo; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.efectivo (
    id_mediopago integer NOT NULL,
    monto double precision NOT NULL,
    CONSTRAINT efectivo_monto_check CHECK ((monto > (0)::double precision))
);


--
-- TOC entry 227 (class 1259 OID 16537)
-- Name: estadia; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.estadia (
    id_estadia integer NOT NULL,
    precio double precision NOT NULL,
    hora_checkin date NOT NULL,
    hora_checkout date NOT NULL,
    id_reserva integer,
    CONSTRAINT estadia_precio_check CHECK ((precio > (0)::double precision))
);


--
-- TOC entry 230 (class 1259 OID 16612)
-- Name: factura; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.factura (
    id_factura integer NOT NULL,
    precio_final double precision NOT NULL,
    nro_factura integer NOT NULL,
    tipo_factura character varying(1) NOT NULL,
    fecha_emision date NOT NULL,
    id_notacredito integer,
    id_resppago integer,
    CONSTRAINT factura_precio_final_check CHECK ((precio_final > (0)::double precision))
);


--
-- TOC entry 233 (class 1259 OID 16666)
-- Name: guarda_una; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.guarda_una (
    id_reserva integer NOT NULL,
    id_habitacion integer NOT NULL
);


--
-- TOC entry 225 (class 1259 OID 16495)
-- Name: habitacion; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.habitacion (
    id_habitacion integer NOT NULL,
    nro_habitacion integer NOT NULL,
    noches_descuento integer NOT NULL,
    cant_camas integer NOT NULL,
    estado character varying(10) NOT NULL,
    tipo_habitacion integer,
    CONSTRAINT habitacion_cant_camas_check CHECK ((cant_camas > 0)),
    CONSTRAINT habitacion_noches_descuento_check CHECK ((noches_descuento > 0))
);


--
-- TOC entry 222 (class 1259 OID 16448)
-- Name: huesped; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.huesped (
    dni integer NOT NULL,
    nombre character varying(50) NOT NULL,
    apellido character varying(50) NOT NULL,
    edad integer NOT NULL,
    fecha_nacimiento date,
    ocupacion character varying(100),
    tipodni character varying(15) NOT NULL,
    mail character varying(100),
    pos_iva character varying(100),
    calle character varying(50) NOT NULL,
    numero integer NOT NULL,
    departamento character varying(50) NOT NULL,
    piso integer NOT NULL,
    cod_postal integer NOT NULL,
    CONSTRAINT huesped_cod_postal_check CHECK ((cod_postal > 0)),
    CONSTRAINT huesped_numero_check CHECK ((numero > 0))
);


--
-- TOC entry 240 (class 1259 OID 16760)
-- Name: individualestandar; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.individualestandar (
    id_tipo integer NOT NULL,
    cant_simple integer NOT NULL,
    CONSTRAINT individualestandar_cant_simple_check CHECK ((cant_simple = 1))
);


--
-- TOC entry 220 (class 1259 OID 16418)
-- Name: juridica; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.juridica (
    cuit character varying(50) NOT NULL,
    razon_social character varying(100),
    calle character varying(50) NOT NULL,
    numero integer NOT NULL,
    departamento character varying(50) NOT NULL,
    piso integer NOT NULL,
    cod_postal integer NOT NULL,
    CONSTRAINT juridica_cod_postal_check CHECK ((cod_postal > 0)),
    CONSTRAINT juridica_numero_check CHECK ((numero > 0))
);


--
-- TOC entry 231 (class 1259 OID 16633)
-- Name: mediopago; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.mediopago (
    id_mediopago integer NOT NULL,
    monto double precision NOT NULL,
    fecha date NOT NULL,
    CONSTRAINT mediopago_monto_check CHECK ((monto > (0)::double precision))
);


--
-- TOC entry 238 (class 1259 OID 16736)
-- Name: monedaextranjera; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.monedaextranjera (
    tipo_moneda character varying(30) NOT NULL,
    id_mediopago integer,
    valor_cambio double precision NOT NULL,
    CONSTRAINT monedaextranjera_valor_cambio_check CHECK ((valor_cambio > (0)::double precision))
);


--
-- TOC entry 229 (class 1259 OID 16603)
-- Name: notacredito; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.notacredito (
    id_notacredito integer NOT NULL,
    monto double precision NOT NULL,
    fecha date NOT NULL,
    CONSTRAINT notacredito_monto_check CHECK ((monto > (0)::double precision))
);


--
-- TOC entry 232 (class 1259 OID 16642)
-- Name: pago; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.pago (
    id_pago integer NOT NULL,
    id_mediopago integer,
    monto double precision NOT NULL,
    fecha_pago date NOT NULL,
    id_resppago integer,
    id_factura integer,
    CONSTRAINT pago_monto_check CHECK ((monto > (0)::double precision))
);


--
-- TOC entry 226 (class 1259 OID 16515)
-- Name: reserva; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.reserva (
    id_reserva integer NOT NULL,
    cant_huespedes integer NOT NULL,
    fecha_inicio date NOT NULL,
    cant_noches integer NOT NULL,
    descuento boolean NOT NULL,
    dni_huesped integer,
    id_habitacion integer,
    CONSTRAINT reserva_cant_huespedes_check CHECK ((cant_huespedes > 0)),
    CONSTRAINT reserva_cant_noches_check CHECK ((cant_noches > 0))
);


--
-- TOC entry 221 (class 1259 OID 16436)
-- Name: responsablepago; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.responsablepago (
    id_resppago integer NOT NULL,
    razon_social character varying(100),
    cuit character varying(50) NOT NULL
);


--
-- TOC entry 239 (class 1259 OID 16749)
-- Name: suite; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.suite (
    id_tipo integer
);


--
-- TOC entry 243 (class 1259 OID 16806)
-- Name: superiorfamilyplan; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.superiorfamilyplan (
    id_tipo integer NOT NULL,
    cant_simple integer NOT NULL,
    cant_dobles integer NOT NULL,
    CONSTRAINT superiorfamilyplan_cant_dobles_check CHECK (((cant_dobles <= 2) AND (cant_dobles > 0))),
    CONSTRAINT superiorfamilyplan_cant_simple_check CHECK (((cant_simple <= 3) AND (cant_simple > 0)))
);


--
-- TOC entry 223 (class 1259 OID 16472)
-- Name: telefono; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.telefono (
    nrotelefono integer NOT NULL,
    dniduenio integer NOT NULL
);


--
-- TOC entry 224 (class 1259 OID 16487)
-- Name: tipohabitacion; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tipohabitacion (
    id_tipo integer NOT NULL,
    costo_noche double precision NOT NULL,
    CONSTRAINT tipohabitacion_costo_noche_check CHECK ((costo_noche > (0)::double precision))
);


--
-- TOC entry 5217 (class 0 OID 16710)
-- Dependencies: 236
-- Data for Name: cheque; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.cheque (id_mediopago, monto, dni_titular) FROM stdin;
\.


--
-- TOC entry 5209 (class 0 OID 16552)
-- Dependencies: 228
-- Data for Name: consumible; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.consumible (id_condumible, nombre, precio, id_estadia) FROM stdin;
\.


--
-- TOC entry 5215 (class 0 OID 16683)
-- Dependencies: 234
-- Data for Name: credito; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.credito (id_mediopago, nro_tarjeta, cuotas, nombre_titular, apellido_titular) FROM stdin;
\.


--
-- TOC entry 5216 (class 0 OID 16697)
-- Dependencies: 235
-- Data for Name: debito; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.debito (id_mediopago, nro_tarjeta, nombre_titular, apellido_titular) FROM stdin;
\.


--
-- TOC entry 5200 (class 0 OID 16389)
-- Dependencies: 219
-- Data for Name: direccion; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.direccion (calle, numero, departamento, piso, cod_postal, localidad, provincia, pais) FROM stdin;
Urquiza	2788	3A	3	3030	San Martin	Santa Fe	Argentina
Ramos Mejía	2788	oeste	11	3030	San Martin	Santa Fe	Argentina
San Martin	1854	este	3	3034	San Lorenzo	Santa Fe	Argentina
Los Teros	984	ocho	4	3050	Angelica	Santa Fe	Argentina
Belgrano	100	pent	10	5030	San Martin	Cordoba	Argentina
\.


--
-- TOC entry 5222 (class 0 OID 16776)
-- Dependencies: 241
-- Data for Name: dobleestandar; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.dobleestandar (id_tipo, cant_simple, cant_dobles) FROM stdin;
\.


--
-- TOC entry 5223 (class 0 OID 16791)
-- Dependencies: 242
-- Data for Name: doblesuperior; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.doblesuperior (id_tipo, cant_simple, cant_king) FROM stdin;
\.


--
-- TOC entry 5218 (class 0 OID 16723)
-- Dependencies: 237
-- Data for Name: efectivo; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.efectivo (id_mediopago, monto) FROM stdin;
\.


--
-- TOC entry 5208 (class 0 OID 16537)
-- Dependencies: 227
-- Data for Name: estadia; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.estadia (id_estadia, precio, hora_checkin, hora_checkout, id_reserva) FROM stdin;
\.


--
-- TOC entry 5211 (class 0 OID 16612)
-- Dependencies: 230
-- Data for Name: factura; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.factura (id_factura, precio_final, nro_factura, tipo_factura, fecha_emision, id_notacredito, id_resppago) FROM stdin;
\.


--
-- TOC entry 5214 (class 0 OID 16666)
-- Dependencies: 233
-- Data for Name: guarda_una; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.guarda_una (id_reserva, id_habitacion) FROM stdin;
\.


--
-- TOC entry 5206 (class 0 OID 16495)
-- Dependencies: 225
-- Data for Name: habitacion; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.habitacion (id_habitacion, nro_habitacion, noches_descuento, cant_camas, estado, tipo_habitacion) FROM stdin;
\.


--
-- TOC entry 5203 (class 0 OID 16448)
-- Dependencies: 222
-- Data for Name: huesped; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.huesped (dni, nombre, apellido, edad, fecha_nacimiento, ocupacion, tipodni, mail, pos_iva, calle, numero, departamento, piso, cod_postal) FROM stdin;
44427691	nacho	mazzoni	22	2003-01-03	estudiante	DNI	nachomazzo@mail.com	resp_inscripto	Urquiza	2788	3A	3	3030
46427684	santiago	martinez	20	2005-10-03	estudiante	DNI	santi@mail.com	resp_inscripto	Ramos Mejía	2788	oeste	11	3030
40427644	agustin	aliendro	26	1999-10-10	contador	DNI	agus@mail.com	resp_inscripto	San Martin	1854	este	3	3034
48427554	alejo	remirez	18	2008-09-15	estudiante	DNI	ale@mail.com	resp_inscripto	Los Teros	984	ocho	4	3050
42424321	martino	marin	24	2001-01-19	estudiante	DNI	martino@mail.com	resp_inscripto	Belgrano	100	pent	10	5030
\.


--
-- TOC entry 5221 (class 0 OID 16760)
-- Dependencies: 240
-- Data for Name: individualestandar; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.individualestandar (id_tipo, cant_simple) FROM stdin;
1	1
\.


--
-- TOC entry 5201 (class 0 OID 16418)
-- Dependencies: 220
-- Data for Name: juridica; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.juridica (cuit, razon_social, calle, numero, departamento, piso, cod_postal) FROM stdin;
\.


--
-- TOC entry 5212 (class 0 OID 16633)
-- Dependencies: 231
-- Data for Name: mediopago; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.mediopago (id_mediopago, monto, fecha) FROM stdin;
\.


--
-- TOC entry 5219 (class 0 OID 16736)
-- Dependencies: 238
-- Data for Name: monedaextranjera; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.monedaextranjera (tipo_moneda, id_mediopago, valor_cambio) FROM stdin;
\.


--
-- TOC entry 5210 (class 0 OID 16603)
-- Dependencies: 229
-- Data for Name: notacredito; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.notacredito (id_notacredito, monto, fecha) FROM stdin;
\.


--
-- TOC entry 5213 (class 0 OID 16642)
-- Dependencies: 232
-- Data for Name: pago; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.pago (id_pago, id_mediopago, monto, fecha_pago, id_resppago, id_factura) FROM stdin;
\.


--
-- TOC entry 5207 (class 0 OID 16515)
-- Dependencies: 226
-- Data for Name: reserva; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.reserva (id_reserva, cant_huespedes, fecha_inicio, cant_noches, descuento, dni_huesped, id_habitacion) FROM stdin;
\.


--
-- TOC entry 5202 (class 0 OID 16436)
-- Dependencies: 221
-- Data for Name: responsablepago; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.responsablepago (id_resppago, razon_social, cuit) FROM stdin;
\.


--
-- TOC entry 5220 (class 0 OID 16749)
-- Dependencies: 239
-- Data for Name: suite; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.suite (id_tipo) FROM stdin;
\.


--
-- TOC entry 5224 (class 0 OID 16806)
-- Dependencies: 243
-- Data for Name: superiorfamilyplan; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.superiorfamilyplan (id_tipo, cant_simple, cant_dobles) FROM stdin;
\.


--
-- TOC entry 5204 (class 0 OID 16472)
-- Dependencies: 223
-- Data for Name: telefono; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.telefono (nrotelefono, dniduenio) FROM stdin;
\.


--
-- TOC entry 5205 (class 0 OID 16487)
-- Dependencies: 224
-- Data for Name: tipohabitacion; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.tipohabitacion (id_tipo, costo_noche) FROM stdin;
1	50800
2	70230
3	90560
4	110500
5	128600
\.


-- Completed on 2025-11-07 18:35:13

--
-- PostgreSQL database dump complete
--

\unrestrict jQW3SgCo5uvCr5sl2fscLNox4zNSVbOEuku2768ebybpE8XdqR9HJ0cuUmEGlW2

