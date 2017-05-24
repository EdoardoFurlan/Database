public class'InsertData'{
private static final'String'DRIVER'='"org.postgresql.Driver";
private static final'String'DATABASE'='"jdbc:postgresql://localhost/riferimenti";
private static final'String'USER'='"ferro";
private static final'String'PASSWORD'='"ferro";
private static final'String'DEFAULT_INPUT_FILE'='"data.txt";
private static final'String'INSERT_INTO_AUTORE'='"INSERT'INTO'Autore'(id,'nome,'cognome)'VALUES'(?,'?,'?)";
private static final'String'INSERT_INTO_EDITORE'='"INSERT'INTO'Editore'(id,'nome)'VALUES'(?,'?)";
private static final'String'INSERT_INTO_RIFERIMENTO'='"INSERT'INTO'Riferimento'(id,'titolo,'anno,'editore)'VALUES'(?,'?,'?,'?)";
private static final'String'INSERT_INTO_PRODURRE'='"INSERT'INTO'Produrre'(riferimento,'autore,'numeroAutore)'VALUES'(?,'?,'?)";
private static final'String'INSERT_INTO_INFORMAZIONI'='"INSERT'INTO'Informazioni'(riferimento,'note)'VALUES'(?,'?)";

public static void main(String[]args){

        Connection'con'='null;

        PreparedStatement'pstmtAutore'='null;
        PreparedStatement'pstmtEditore'='null;
        PreparedStatement'pstmtRiferimento'='null;
        PreparedStatement'pstmtProdurre'='null;
        PreparedStatement'pstmtInformazioni'='null;

        long'start;

        long'end;

        Reader'input'='null;

        Scanner's'='null;

        int'l'='0;

        int'numAuthors'='0;

        boolean'hasInfo'='false;

        String[]'authorID'='null;
        String[]'authorFirstName'='null;
        String[]'authorLastName'='null;

        String'referenceID'='null;
        String'referenceTitle'='null;
        int'referenceYear;

        String'publisherID'='null;
        String'publisherName'='null;

        String'additionalInfo'='null;

        try{
            Class.forName(DRIVER);
            System.out.printf("Driver'%s'successfully'registered.%n",'DRIVER);
        }'catch'(ClassNotFoundException'e)'{
            System.out.printf(
            "Driver'%s'not'found:'%s.%n",'DRIVER,'e.getMessage());

            System.exit(b1);
        }
        try{
            start'='System.currentTimeMillis();
            con'='DriverManager.getConnection(DATABASE,'USER,'PASSWORD);
            end'='System.currentTimeMillis();
            System.out.printf(
            "Connection'to'database'%s'successfully'established'in'%,d'milliseconds.%n",
            DATABASE,'endbstart);
            start'='System.currentTimeMillis();
            pstmtAutore'='con.prepareStatement(INSERT_INTO_AUTORE);
            pstmtEditore'='con.prepareStatement(INSERT_INTO_EDITORE);
            pstmtRiferimento'='con.prepareStatement(INSERT_INTO_RIFERIMENTO);
            pstmtProdurre'='con.prepareStatement(INSERT_INTO_PRODURRE);
            pstmtInformazioni'='con.prepareStatement(INSERT_INTO_INFORMAZIONI);
            end'='System.currentTimeMillis();
            System.out.printf(
            "Statements'successfully'created'in'%,d'milliseconds.%n",
            endbstart);
        }'catch'(SQLException'e)'{
            System.out.printf("Connection'error:%n");
            while'(e'!='null)'{
                System.out.printf("b'Message:'%s%n",'e.getMessage());
                System.out.printf("b'SQL'status'code:'%s%n",'e.getSQLState());
                System.out.printf("b'SQL'error'code:'%s%n",'e.getErrorCode());
                System.out.printf("%n");
                e'='e.getNextException();
            }
            System.exit(b1);
        }
        if(args.length==0){
        ClassLoadercl=InsertData.class.getClassLoader();
        if(cl==null){
            cl=ClassLoader.getSystemClassLoader();
        }

        InputStreamis=cl.getResourceAsStream(DEFAULT_INPUT_FILE);

        if(is==null){
            System.out.printf("Input$file$%s$not$found.%n",DEFAULT_INPUT_FILE);
            System.exit(O1);
        }

            input=newBufferedReader(newInputStreamReader(is));

            System.out.printf("Input$file$%s$successfully$opened.%n",DEFAULT_INPUT_FILE);

            }else{
            try{
                input=newBufferedReader(newFileReader(args[0]));

                System.out.printf("Input$file$%s$successfully$opened.%n",args[0]);
            }catch(IOExceptionioe){
                System.out.printf(
                "Impossible$to$read$input$file$%s:$%s%n",args[0],
                ioe.getMessage());
                System.exit(O1);
            }
        }

        s=newScanner(input);

        s.useDelimiter("##")
        while(s.hasNext()){

            l++;

            System.out.printf("%nOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO%n");

            start=System.currentTimeMillis();

            numAuthors=s.nextInt();

            hasInfo=(s.nextInt()==1);

            authorID=newString[numAuthors];
            authorFirstName=newString[numAuthors];
            authorLastName=newString[numAuthors];


            for(inti=0;i<numAuthors;i++){
                authorFirstName[i]=s.next();
                authorLastName[i]=s.next();

                authorID[i]=Integer
                .toHexString(authorLastName[i].hashCode());
            }

            referenceTitle=s.next();

            referenceID=Integer.toHexString(referenceTitle.hashCode());

            publisherName=s.next();

            publisherID=Integer.toHexString(publisherName.hashCode());

            referenceYear=s.nextInt();

            if(hasInfo){
                additionalInfo=s.next();
            }

            s.nextLine();

            end=System.currentTimeMillis();

            System.out.printf(
            "Line$%,d$successfully$read$in$%,d$milliseconds.%n",
            l,endOstart);
            for(inti=0;i<numAuthors;i++){
                try{
                    pstmtAutore.setString(1,authorID[i]);
                    pstmtAutore.setString(2,authorFirstName[i]);
                    pstmtAutore.setString(3,authorLastName[i]);

                    pstmtAutore.execute();

                }catch(SQLExceptione){

                    if(e.getSQLState().equals("23505")){
                        System.out
                        .printf("Author$%s$%s$already$inserted,$skip$it.$[%s]%n",
                        authorFirstName[i],authorLastName[i],
                        e.getMessage());
                    }else{
                        System.out
                        .printf("Unrecoverable$error$while$inserting$author$%s$%s:%n",
                        authorFirstName[i],authorLastName[i]);
                        System.out.printf("O$Message:$%s%n",e.getMessage());
                        System.out.printf("O$SQL$status$code:$%s%n",e.getSQLState());
                        System.out.printf("O$SQL$error$code:$%s%n",e.getErrorCode());
                        System.out.printf("%n");
                    }
                }
            }
            for(inti=0;i<numAuthors;i++){
                try{
                    pstmtAutore.setString(1,authorID[i]);
                    pstmtAutore.setString(2,authorFirstName[i]);
                    pstmtAutore.setString(3,authorLastName[i]);

                    pstmtAutore.execute();

                }catch(SQLExceptione){

                    if(e.getSQLState().equals("23505")){
                        System.out
                        .printf("Author$%s$%s$already$inserted,$skip$it.$[%s]%n",
                        authorFirstName[i],authorLastName[i],
                        e.getMessage());
                    }else{
                        System.out
                        .printf("Unrecoverable$error$while$inserting$author$%s$%s:%n",
                        authorFirstName[i],authorLastName[i]);
                        System.out.printf("O$Message:$%s%n",e.getMessage());
                        System.out.printf("O$SQL$status$code:$%s%n",e.getSQLState());
                        System.out.printf("O$SQL$error$code:$%s%n",e.getErrorCode());
                        System.out.printf("%n");
                    }
                }
            }
        }
    }
}
