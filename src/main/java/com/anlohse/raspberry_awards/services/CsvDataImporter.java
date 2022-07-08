package com.anlohse.raspberry_awards.services;

import com.anlohse.raspberry_awards.domain.Movie;
import com.anlohse.raspberry_awards.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class CsvDataImporter {

    private final MovieRepository movieRepository;

    @PostConstruct
    @Transactional
    public void runCsvImport() throws IOException {
        InputStream is = CsvDataImporter.class.getResourceAsStream("/data/movielist.csv");
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.EXCEL.builder().setHeader("year","title","studios","producers","winner").setDelimiter(';').setSkipHeaderRecord(true).setTrim(true).build());
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();
        List<Movie> movieList = StreamSupport.stream(csvRecords.spliterator(), false)
                .map(this::createMovie)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        movieRepository.saveAll(movieList);
    }

    private List<Movie> createMovie(CSVRecord csvRecord) {
        String[] producers = breakProducers(csvRecord.get("producers"));
        return Stream.of(producers).map(producer -> Movie.builder()
                .year(Integer.parseInt(csvRecord.get("year")))
                .title(csvRecord.get("title"))
                .studios(csvRecord.get("studios"))
                .producer(producer.trim())
                .winner(csvRecord.get("winner"))
                .build())
                .collect(Collectors.toList());
    }

    private String[] breakProducers(String producers) {
        return producers.split("(,|\\sand\\s)");
    }

}
