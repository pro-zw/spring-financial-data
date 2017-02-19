package org.wzstudio.spring.finance.data.dto.outbound;

import org.joda.time.LocalDate;
import org.wzstudio.spring.finance.data.domain.ETFIndex;
import org.wzstudio.spring.finance.data.domain.ETFIndexRatio;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableList;

/**
 * ETF index valuation factors, e.g. PE & PB ratios
 */
public class ETFIndexValuationModel {
    /**
     * The index outline
     */
    public final ETFIndexModel index;

    /**
     * The PE ratio date labels
     */
    public final List<LocalDate> peRecordDates;

    /**
     * The PE ratios
     */
    public final List<Float> peRatios;

    /**
     * The PB ratio date labels
     */
    public final List<LocalDate> pbRecordDates;

    /**
     * The PB ratios
     */
    public final List<Float> pbRatios;

    /**
     * The distribution yield date labels
     */
    public final List<LocalDate> distributionYieldRecordDates;

    /**
     * The distribution yields
     */
    public final List<Float> distributionYields;

    /**
     * Constructor
     */
    public ETFIndexValuationModel(ETFIndex index) {

        this.index = new ETFIndexModel(index);

        List<ETFIndexRatio> peRatios = index.peRatios();
        this.peRecordDates = unmodifiableList(
                peRatios.stream().map(data -> data.recordDate).collect(Collectors.toList())
        );
        this.peRatios = unmodifiableList(
                peRatios.stream().map(data -> data.data).collect(Collectors.toList())

        );

        List<ETFIndexRatio> pbRatios = index.pbRatios();
        this.pbRecordDates = unmodifiableList(
                pbRatios.stream().map(data -> data.recordDate).collect(Collectors.toList())
        );
        this.pbRatios = unmodifiableList(
                pbRatios.stream().map(data -> data.data).collect(Collectors.toList())
        );

        List<ETFIndexRatio> distributionYields = index.distributionYields();
        this.distributionYieldRecordDates = unmodifiableList(
                distributionYields.stream().map(data -> data.recordDate).collect(Collectors.toList())
        );
        this.distributionYields = unmodifiableList(
                distributionYields.stream().map(data -> data.data).collect(Collectors.toList())
        );
    }
}
