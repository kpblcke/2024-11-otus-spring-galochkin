import React from 'react';
import CssBaseline from "@mui/material/CssBaseline";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import {Typography} from "@mui/material";

const ErrorContainer = (props) => {
    const {error} = props;
    if (error !== "") {
        return (
            <React.Fragment>
                <CssBaseline/>
                <Container maxWidth="lg">
                    <Box sx={{bgcolor: '#ff000050', p: 1, borderRadius: 4, mt: 2}}>
                        <Typography variant="h4" gutterBottom>
                            {error}
                        </Typography>
                    </Box>
                </Container>
            </React.Fragment>
        )
    }
}

export default ErrorContainer;