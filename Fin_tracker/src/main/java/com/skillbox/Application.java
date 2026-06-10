package com.skillbox;


import com.skillbox.controller.MainMenuController;
import com.skillbox.data.repository.AccountRepository;
import com.skillbox.data.repository.AnalyticRepository;
import com.skillbox.data.repository.TransactionRepository;
import com.skillbox.data.repository.impl.AccountRepositoryImpl;
import com.skillbox.data.repository.impl.AnalyticRepositoryImpl;
import com.skillbox.data.repository.impl.TransactionRepositoryImpl;
import com.skillbox.exception.EnvironmentParameterNotFoundException;
import com.skillbox.service.TransactionService;
import com.skillbox.service.TransactionServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {
    private static String accountFilename;
    private static String transactionFilename;
    private static String analyticDirectory;

    public static void main(String[] args) {
        loadArgs(args);

        AccountRepository accountReader = new AccountRepositoryImpl(accountFilename);
        TransactionRepository transactionReader = new TransactionRepositoryImpl(transactionFilename);
        AnalyticRepository analyticRepository = new AnalyticRepositoryImpl(analyticDirectory);

        TransactionService transactionService = new TransactionServiceImpl(transactionReader, accountReader);

        new MainMenuController(transactionService, analyticRepository).start();
    }

    private static void loadArgs(String[] args){
        try {
            accountFilename = getArg("accountFileName", 0, args);
            transactionFilename = getArg("transactionFileName", 1, args);
            analyticDirectory = getArg("analyticDirectoryName", 2, args);
        } catch (Exception e) {
            log.error("Необходимо указать имена файлов для входных данных аккаунтов и транзакций, а также для выходного файла.");
            throw e;
        }
    }

    private static String getArg(String parameterName, int index, String[] args) {
        if (index >= args.length) {
            throw new EnvironmentParameterNotFoundException(parameterName + " не задан");
        }
        return "Fin_tracker/files/" + args[index];
    }
}
