$(document).ready(function() {
    // Define all view components we need
    var chartsTypesBtnGroup = $('#charts-types').first();
    var chartsRow = $('#charts-row').first();

    var valuationBtn = $(
        '<button type="button" class="btn btn-default" id="valuation-btn">Valuation</button>'
    );

    var marketFactsBtn = $(
        '<button type="button" class="btn btn-default" id="market-facts-btn">Market Facts</button>'
    );

    var peRatiosCanvasDiv = $(
        '<div class="col-lg-6 col-md-12"><canvas id="pe-ratios-chart"></canvas></div>'
    );
    var peRatiosCanvas = peRatiosCanvasDiv.children('#pe-ratios-chart').first();

    var pbRatiosCanvasDiv = $(
        '<div class="col-lg-6 col-md-12"><canvas id="pb-ratios-chart"></canvas></div>'
    );
    var pbRatiosCanvas = pbRatiosCanvasDiv.children('#pb-ratios-chart').first();

    var netAssetsesCanvasDiv = $(
        '<div class="col-lg-6 col-md-12"><canvas id="net-assetses-chart"></canvas></div>'
    );

    var netAssetsesCanvas = netAssetsesCanvasDiv.children('#net-assetses-chart').first();

    var sharesOutstandingsCanvasDiv = $(
        '<div class="col-lg-6 col-md-12"><canvas id="shares-outstandings-chart"></canvas></div>'
    );

    var sharesOutstandingsCanvas = sharesOutstandingsCanvasDiv.children('#shares-outstandings-chart').first();

    var numberOfHoldingsesCanvasDiv = $(
        '<div class="col-lg-6 col-md-12"><canvas id="number-of-holdingses-chart"></canvas></div>'
    );

    var numberOfHoldingsesCanvas = numberOfHoldingsesCanvasDiv.children('#number-of-holdingses-chart').first();

    var closingPricesCanvasDiv = $(
        '<div class="col-lg-6 col-md-12"><canvas id="closing-prices-chart"></canvas></div>'
    );

    var closingPricesCanvas = closingPricesCanvasDiv.children('#closing-prices-chart').first();

    // Views
    var chartsDatasets = function(chartLabel, dataLabels, data) {
        return {
            labels: dataLabels,
            datasets: [
                {
                    label: chartLabel,
                    fill: false,
                    lineTension: 0.2,
                    backgroundColor: "rgba(75,192,192,0.4)",
                    borderColor: "rgba(75,192,192,1)",
                    borderCapStyle: 'butt',
                    borderDash: [],
                    borderDashOffset: 0.0,
                    borderJoinStyle: 'miter',
                    pointBorderColor: "rgba(75,192,192,1)",
                    pointBackgroundColor: "#fff",
                    pointBorderWidth: 1,
                    pointHoverRadius: 5,
                    pointHoverBackgroundColor: "rgba(75,192,192,1)",
                    pointHoverBorderColor: "rgba(220,220,220,1)",
                    pointHoverBorderWidth: 2,
                    pointRadius: 1,
                    pointHitRadius: 10,
                    data: data,
                    spanGaps: false
                }
            ]
        };
    };

    var addValuationBtn = function() {
        if (!chartsTypesBtnGroup.children('#valuation-btn').length) {
            valuationBtn.appendTo(chartsTypesBtnGroup);
        }
    };

    var addMarketFactsBtn = function() {
        if (!chartsTypesBtnGroup.children('market-facts-btn').length) {
            marketFactsBtn.prependTo(chartsTypesBtnGroup);
        }
    };

    var addPERatiosCanvas = function() {
        if (!chartsRow.find('#pe-ratios-chart').length) {
            peRatiosCanvasDiv.prependTo(chartsRow);
        }
    };

    var addPBRatiosCanvas = function() {
        if (!chartsRow.find('#pb-ratios-chart').length) {
            pbRatiosCanvasDiv.appendTo(chartsRow);
        }
    };

    var addNetAssetsesCanvas = function() {
        if (!chartsRow.find('#net-assetses-chart').length) {
            netAssetsesCanvasDiv.appendTo(chartsRow);
        }

    };

    var addSharesOutstandingsCanvas = function() {
        if (!chartsRow.find('#shares-outstandings-chart').length) {
            sharesOutstandingsCanvasDiv.appendTo(chartsRow);
        }

    };

    var addNumberOfHoldingsesCanvas = function() {
        if (!chartsRow.find('#number-of-holdingses-chart').length) {
            numberOfHoldingsesCanvasDiv.appendTo(chartsRow);
        }

    };

    var addClosingPricesCanvas = function() {
        if (!chartsRow.find('#closing-prices-chart').length) {
            closingPricesCanvasDiv.appendTo(chartsRow);
        }
    };

    var activeMarketFacts = function() {
        marketFactsBtn.addClass('active');
        valuationBtn.removeClass('active');

        netAssetsesCanvasDiv.show();
        sharesOutstandingsCanvasDiv.show();
        numberOfHoldingsesCanvasDiv.show();
        closingPricesCanvasDiv.show();
        peRatiosCanvasDiv.hide();
        pbRatiosCanvasDiv.hide();
    };
    marketFactsBtn.on('click', activeMarketFacts);

    var activeValuation = function() {
        marketFactsBtn.removeClass('active');
        valuationBtn.addClass('active');

        netAssetsesCanvasDiv.hide();
        sharesOutstandingsCanvasDiv.hide();
        numberOfHoldingsesCanvasDiv.hide();
        closingPricesCanvasDiv.hide();
        peRatiosCanvasDiv.show();
        pbRatiosCanvasDiv.show();
    };
    valuationBtn.on('click', activeValuation);

    // Controller
    $.getJSON("/api/etfs/" + indexId + "/valuation", function(data) {
        if (data['peRatios'].length) {
            addValuationBtn();
            addPERatiosCanvas();

            new Chart(peRatiosCanvas, {
                type: 'line',
                data: chartsDatasets(
                    'PE Ratios',
                    data['peRecordDates'],
                    data['peRatios']
                )
            });
        }

        if (data['pbRatios'].length) {
            addValuationBtn();
            addPBRatiosCanvas();

            new Chart(pbRatiosCanvas, {
                type: 'line',
                data: chartsDatasets(
                    'PB Ratios',
                    data['pbRecordDates'],
                    data['pbRatios']
                )
            });
        }
    });

    $.getJSON("/api/etfs/" + indexId + "/market-facts", function(data) {
        if (data['closingPrices'].length) {
            addMarketFactsBtn();

            addNetAssetsesCanvas();
            addSharesOutstandingsCanvas();
            addClosingPricesCanvas();
            addNumberOfHoldingsesCanvas();

            new Chart(netAssetsesCanvas, {
                type: 'line',
                data: chartsDatasets(
                    'Net Assets',
                    data['netAssetsDates'],
                    data['netAssetses']
                ),
                options: {
                    scales: {
                        yAxes: [
                            {
                                ticks: {
                                    callback: function(label, index, labels) {
                                        return (label / 1000).toLocaleString() + 'k';
                                    }
                                },
                                scaleLabel: {
                                    display: true,
                                    labelString: data['index']['netAssets']['data'].substr(0, 3)
                                }
                            }
                        ]
                    }
                }
            });

            new Chart(sharesOutstandingsCanvas, {
                type: 'line',
                data: chartsDatasets(
                    'Shares Outstanding',
                    data['sharesOutstandingDates'],
                    data['sharesOutstandings']
                ),
                options: {
                    scales: {
                        yAxes: [
                            {
                                ticks: {
                                    callback: function(label, index, labels) {
                                        return (label / 1000).toLocaleString() + 'k';
                                    }
                                }
                            }
                        ]
                    }
                }
            });

            new Chart(closingPricesCanvas, {
                type: 'line',
                data: chartsDatasets(
                    'Closing Prices',
                    data['closingPriceDates'],
                    data['closingPrices']
                ),
                options: {
                    scales: {
                        yAxes: [
                            {
                                ticks: {
                                    callback: function(label, index, labels) {
                                        return label.toFixed(2).toLocaleString();
                                    }
                                },
                                scaleLabel: {
                                    display: true,
                                    labelString: data['index']['closingPrice']['data'].substr(0, 3)
                                }
                            }
                        ]
                    }
                }
            });

            new Chart(numberOfHoldingsesCanvas, {
                type: 'line',
                data: chartsDatasets(
                    'Number of Holdings',
                    data['numberOfHoldingsDates'],
                    data['numberOfHoldingses']
                ),
                options: {
                    scales: {
                        yAxes: [
                            {
                                ticks: {
                                    callback: function(label, index, labels) {
                                        return label.toFixed(1).toLocaleString();
                                    }
                                }
                            }
                        ]
                    }
                }
            });

            activeMarketFacts();
        }
    });
});
